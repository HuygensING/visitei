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

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public class Element extends SubNode {

  public static final String DIV_TAG = "div";
  public static final String SPAN_TAG = "span";

  public static Element newDivElement(String className) {
    return new Element(DIV_TAG).withAttribute("class", className);
  }

  public static Element newSpanElement(String className) {
    return new Element(SPAN_TAG).withAttribute("class", className);
  }

  // -------------------------------------------------------------------

  private String name;
  private final Map<String, String> attributes;

  private final List<Node> nodes;

  public Element(String name, Map<String, String> attrs) {
    this.name = name;
    this.attributes = attrs;
    nodes = Lists.newArrayList();
  }

  public Element(String name) {
    this(name, XmlUtils.newAttributes());
  }

  public Element withAttribute(String key, String value) {
    setAttribute(key, value);
    return this;
  }

  /**
   * Create a new element with the same name and attributes as the given element, but none of its child nodes or end/start lines/columns
   * @param element the element to copy
   * @return the copied element, with the same name and attributes as the given element.
   */
  public static Element copyOf(Element element) {
    Element copy = new Element(element.getName());
    for (Entry<String, String> attribute : element.attributes.entrySet()) {
      copy.setAttribute(attribute.getKey(), attribute.getValue());
    }
    return copy;
  }

  /**
   * @deprecated As of 0.4.7, use new {@link #Element(String)}.{@link #withAttribute(String, String)} instead
   * @param name  element name
   * @param key   attribute key
   * @param value attribute value
   */
  @Deprecated
  public Element(String name, String key, String value) {
    this(name, XmlUtils.newAttributes());
    attributes.put(key, value);
  }

  // --- Visiting ------------------------------------------------------

  @Override
  public Traversal accept(Visitor visitor) {
    if (visitor.enterElement(this) == Traversal.NEXT) {
      for (Node node : nodes) {
        if (node.accept(visitor) == Traversal.STOP) {
          break;
        }
      }
    }
    return visitor.leaveElement(this);
  }

  // --- Structure -----------------------------------------------------

  public void addNode(Node node) {
    nodes.add(node);
  }

  public boolean hasChildren() {
    return (nodes.size() != 0);
  }

  public boolean hasNoChildren() {
    return (nodes.size() == 0);
  }

  public List<Node> getNodes() {
    return nodes;
  }

  public String getName() {
    return name;
  }

  public void setName(String value) {
    name = value;
  }

  public boolean hasName(String name) {
    return this.name.equals(name);
  }

  /**
   * @return the line number in the source xml where the start tag of this element ends, if available;
   *         -1 otherwise
   */
  @Override
  public int getStartLine() {
    return super.getStartLine();
  }

  /**
   * @return the column number in the source xml where the start tag of this element ends, if available;
   *         -1 otherwise
   */
  @Override
  public int getStartColumn() {
    return super.getStartColumn();
  }

  // --- Attributes ----------------------------------------------------

  public static final String LANGUAGE = "lang";
  public static final String RENDITION = "rend";
  public static final String TYPE = "type";

  public Map<String, String> getAttributes() {
    return attributes;
  }

  public String getAttribute(String key) {
    String value = attributes.get(key);
    return (value != null) ? value : "";
  }

  public String getAttribute(String key, String defaultValue) {
    String value = attributes.get(key);
    return (value != null) ? value : defaultValue;
  }

  /**
   * Returns an integer attribute value, or the specified default value
   * if the attribute is missing or if conversion to integer fails.
   *
   * @param key the attribute key
   * @param defaultValue the specified default
   * @return an integer attribute value, or the specified default value
   */
  public int getIntAttribute(String key, int defaultValue) {
    String value = attributes.get(key);
    if (value != null) {
      try {
        return Integer.valueOf(value);
      } catch (NumberFormatException e) {
        // ignore
      }
    }
    return defaultValue;
  }

  /**
   * Returns a double attribute value, or the specified default value
   * if the attribute is missing or if conversion to double fails.
   *
   * @param key the attribute key
   * @param defaultValue the specified default
   * @return a double attribute value, or the specified default value
   */
  public double getDoubleAttribute(String key, double defaultValue) {
    String value = attributes.get(key);
    if (value != null) {
      try {
        return Double.valueOf(value);
      } catch (NumberFormatException e) {
        // ignore
      }
    }
    return defaultValue;
  }

  public boolean hasAttribute(String key) {
    String value = attributes.get(key);
    return (value != null) && (value.length() != 0);
  }

  public boolean hasAttribute(String key, String value) {
    // TODO decide whether to check for null value
    return value.equals(attributes.get(key));
  }

  public void removeAttribute(String key) {
    attributes.remove(key);
  }

  public void setAttribute(String key, String value) {
    attributes.put(key, value);
  }

  public void copyAttributeFrom(Element source, String key) {
    String value = source.getAttribute(key);
    if (value.length() != 0) {
      setAttribute(key, value);
    }
  }

  public String getLanguage() {
    return getAttribute(LANGUAGE);
  }

  public boolean hasLanguage() {
    return hasAttribute(LANGUAGE);
  }

  public boolean hasLanguage(String value) {
    return hasAttribute(LANGUAGE, value);
  }

  public void setLanguage(String value) {
    setAttribute(LANGUAGE, value);
  }

  public String getRendition() {
    return getAttribute(RENDITION);
  }

  public boolean hasRendition() {
    return hasAttribute(RENDITION);
  }

  public boolean hasRendition(String value) {
    return hasAttribute(RENDITION, value);
  }

  public String getType() {
    return getAttribute(TYPE);
  }

  public boolean hasType() {
    return hasAttribute(TYPE);
  }

  public boolean hasType(String value) {
    return hasAttribute(TYPE, value);
  }

  // --- Output --------------------------------------------------------

  public void appendOpenTagTo(StringBuilder builder) {
    appendTagWithAttributesTo(builder, false);
  }

  public void appendCloseTagTo(StringBuilder builder) {
    builder.append('<').append('/').append(name).append('>');
  }

  public void appendEmptyElementTagTo(StringBuilder builder) {
    appendTagWithAttributesTo(builder, true);
  }

  private void appendTagWithAttributesTo(StringBuilder builder, boolean emptyTag) {
    builder.append('<').append(name);
    for (Map.Entry<String, String> entry : attributes.entrySet()) {
      builder.append(' ').append(entry.getKey()).append('=');
      builder.append('"');
      appendAttributeValue(builder, entry.getValue());
      builder.append('"');
    }
    builder.append(emptyTag ? "/>" : ">");
  }

  private void appendAttributeValue(StringBuilder builder, String value) {
    int n = value.length();
    for (int i = 0; i < n; i++) {
      char c = value.charAt(i);
      switch (c) {
      case '<':
        builder.append("&lt;");
        break;
      case '>':
        builder.append("&gt;");
        break;
      case '&':
        builder.append("&amp;");
        break;
      default:
        builder.append(c);
        break;
      }
    }
  }

  public Set<String> getAttributeNames() {
    return this.attributes.keySet();
  }

  @Override
  public String toString() {
    List<String> attributes1 = Lists.newArrayList();
    for (String attributeName : this.attributes.keySet()) {
      attributes1.add(MessageFormat.format(" {0}=\"{1}\"", attributeName, getAttribute(attributeName)));
    }
    Collections.sort(attributes1);
    return MessageFormat.format("<{0}{1}>", getName(), Joiner.on("").join(attributes1));
  }

}
