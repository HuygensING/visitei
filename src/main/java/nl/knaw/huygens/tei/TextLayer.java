package nl.knaw.huygens.tei;

public class TextLayer {

  private final StringBuilder builder;

  public TextLayer() {
    builder = new StringBuilder();
  }

  public int length() {
    return builder.length();
  }

  @Override
  public String toString() {
    return builder.toString();
  }

  // -------------------------------------------------------------------

  public void addLiteral(Object literal) {
    if (literal != null) {
      builder.append(literal.toString());
    }
  }

  public void addOpenTag(Element element) {
    if (element != null) {
      element.appendOpenTagTo(builder);
    }
  }

  public void addOpenTag(String elementName) {
    builder.append('<').append(elementName).append('>');
  }

  public void addCloseTag(Element element) {
    if (element != null) {
      element.appendCloseTagTo(builder);
    }
  }

  public void addCloseTag(String elementName) {
    builder.append('<').append('/').append(elementName).append('>');
  }

  public void addEmptyElementTag(Element element) {
    element.appendEmptyElementTagTo(builder);
  }

  public void addEmptyElementTag(String elementName) {
    builder.append('<').append(elementName).append('/').append('>');
  }

  public void addElement(Element element, Object content) {
    addOpenTag(element);
    addLiteral(content);
    addCloseTag(element);
  }

}
