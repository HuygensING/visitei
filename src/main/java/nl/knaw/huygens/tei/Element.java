package nl.knaw.huygens.tei;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

public class Element extends Node {

  public static final String DIV_TAG = "div";
  public static final String SPAN_TAG = "span";

  public static Element newDivElement(String className) {
    return new Element(DIV_TAG, "class", className);
  }

  public static Element newSpanElement(String className) {
    return new Element(SPAN_TAG, "class", className);
  }

  // -------------------------------------------------------------------

  private final String name;
  private final Map<String, String> attributes;

  private Element parent;
  private final List<Node> nodes;

  public Element(String name, Map<String, String> attrs) {
    this.name = name;
    this.attributes = attrs;
    this.parent = null;
    nodes = Lists.newArrayList();
  }

  public Element(String name, String key, String value) {
    this(name, XmlUtils.newAttributes());
    attributes.put(key, value);
  }

  public Element(String name) {
    this(name, XmlUtils.newAttributes());
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

  public Element getParent() {
    return parent;
  }

  public void setParent(Element parent) {
    this.parent = parent;
  }

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

  public String getAttribute(String key) {
    String value = attributes.get(key);
    return (value != null) ? value : "";
  }

  public String getAttribute(String key, String defaultValue) {
    String value = attributes.get(key);
    return (value != null) ? value : defaultValue;
  }

  public boolean hasAttribute(String key) {
    String value = attributes.get(key);
    return (value != null) && (value.length() != 0);
  }

  public boolean hasAttribute(String key, String value) {
    // TODO decide whether to check for null value
    return value.equals(attributes.get(key));
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

}
