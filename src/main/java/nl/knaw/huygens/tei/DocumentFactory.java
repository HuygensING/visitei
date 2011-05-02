package nl.knaw.huygens.tei;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import org.apache.xerces.parsers.SAXParser;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

public class DocumentFactory extends DefaultHandler2 {

  private final Deque<Element> elementStack;
  private final Document document;

  public DocumentFactory(String xml) {
    elementStack = new ArrayDeque<Element>();
    document = new Document();

    SAXParser parser = new SAXParser();
    try {
      parser.setContentHandler(this);
      parser.setEntityResolver(this);
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
  public void startDocument() throws SAXException {}

  @Override
  public void endDocument() throws SAXException {}

  @Override
  public void startElement(String uri, String localName, String name, Attributes attrs) throws SAXException {
    Map<String, String> attributes = XmlUtils.convertSAXAttributes(attrs);
    Element element = new Element(name, attributes);
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
    elementStack.pop();
  }

  @Override
  public void characters(char[] chars, int start, int length) throws SAXException {
    Text text = new Text(chars, start, length);
    elementStack.peek().addNode(text);
  }

}
