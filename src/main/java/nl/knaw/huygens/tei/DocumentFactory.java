package nl.knaw.huygens.tei;

/*
 * #%L
 * VisiTEI
 * =======
 * Copyright (C) 2011 - 2016 Huygens ING
 * =======
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.xerces.parsers.SAXParser;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

import com.google.common.collect.Maps;

public class DocumentFactory extends DefaultHandler2 {

  private enum Section {
    headNodes, elements, footNodes
  }

  private final Deque<Element> elementStack;
  private final Document document;
  private Locator locator;
  private boolean preserveNameSpacePrefix;
  private Section currentSection = Section.headNodes;

  private Map<String, String> prefixMap = Maps.newHashMap();

  public DocumentFactory(String xml, boolean _preserveNamespacePrefix) {
    preserveNameSpacePrefix = _preserveNamespacePrefix;
    elementStack = new ArrayDeque<Element>();
    document = new Document();
    document.setXml(xml);

    SAXParser parser = new SAXParser();
    try {
      parser.setContentHandler(this);
      parser.setEntityResolver(this);
      parser.setProperty("http://xml.org/sax/properties/lexical-handler", this);
      parser.parse(new InputSource(new StringReader(xml)));
    } catch (SAXException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public Document getDocument() {
    return document;
  }

  // --- entity resolver section ---------------------------------------

  private static final String CHAR_ENTITIES = "<!ENTITY mdash \"&#8212;\" >" // em-dash
      + "<!ENTITY ldquo \"&#8220;\" >" // left double quote
      + "<!ENTITY rdquo \"&#8221;\" >" // right double quote
      + "<!ENTITY nbsp \"&#160;\" >"; // non-breaking space

  @Override
  public InputSource getExternalSubset(String name, String baseURI) throws SAXException, IOException {
    return new InputSource(new StringReader(CHAR_ENTITIES));
  }

  // --- content handler section ---------------------------------------

  @Override
  public void startDocument() throws SAXException {
    // System.out.println(locator.getLineNumber() + ":" + locator.getColumnNumber());
    setStartPosition(document);
  }

  @Override
  public void endDocument() throws SAXException {
    // System.out.println(locator.getLineNumber() + ":" + locator.getColumnNumber());
    setEndPosition(document);
  }

  @Override
  public void startPrefixMapping(String prefix, String uri) throws SAXException {
    super.startPrefixMapping(prefix, uri);
    prefixMap.put(prefix, uri);
  }

  @Override
  public void startElement(String uri, String localName, String name, Attributes attrs) throws SAXException {
    currentSection = Section.elements;
    // System.out.println(locator.getLineNumber() + ":" + locator.getColumnNumber());
    Map<String, String> attributes = XmlUtils.convertSAXAttributes(attrs, preserveNameSpacePrefix);
    Element element = new Element(name, attributes);
    setStartPosition(element);
    if (preserveNameSpacePrefix && !prefixMap.isEmpty()) {
      Set<Entry<String, String>> entrySet = this.prefixMap.entrySet();
      for (Entry<String, String> entry : entrySet) {
        element.setAttribute("xmlns:" + entry.getKey(), entry.getValue());
      }
      prefixMap.clear();
    }
    Element parent = elementStack.peek();
    if (parent == null) {
      document.setRoot(element);
    } else {
      parent.addNode(element);
      element.setParent(parent);
    }
    elementStack.push(element);
  }

  @Override
  public void endElement(String uri, String localName, String name) throws SAXException {
    // System.out.println(locator.getLineNumber() + ":" + locator.getColumnNumber());
    Element e = elementStack.pop();
    setEndPosition(e);
  }

  @Override
  public void characters(char[] chars, int start, int length) throws SAXException {
    // System.out.println(locator.getLineNumber() + ":" + locator.getColumnNumber());
    Text text = new Text(chars, start, length);
    addNonElementNode(text);
  }

  @Override
  public void setDocumentLocator(Locator locator) {
    this.locator = locator;
  }

  @Override
  public void comment(char[] text, int start, int length) throws SAXException {
    String comments = new String(text, start, length);
    Comment comment = new Comment(comments);
    addNonElementNode(comment);
  }

  @Override
  public void processingInstruction(String target, String data) throws SAXException {
    //    Element parent = elementStack.peek();
    ProcessingInstruction pi = new ProcessingInstruction(target, data);
    addNonElementNode(pi);
  }

  private void addNonElementNode(SubNode node) {
    setEndPosition(node);
    Element parent = elementStack.peek();
    if (parent != null) {
      parent.addNode(node);
      node.setParent(parent);
    } else if (currentSection.equals(Section.headNodes)) {
      document.getHeadNodes().add(node);
    } else {
      currentSection = Section.footNodes;
      document.getFootNodes().add(node);
    }
  }

  private void setStartPosition(Node node) {
    node.setStartLine(locator.getLineNumber());
    node.setStartColumn(locator.getColumnNumber());
  }

  private void setEndPosition(Node node) {
    node.setEndLine(locator.getLineNumber());
    node.setEndColumn(locator.getColumnNumber());
  }

}
