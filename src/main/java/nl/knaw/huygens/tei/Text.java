package nl.knaw.huygens.tei;

public class Text extends Node {

  private final String text;
  private Element parent;

  public Text(char[] chars, int start, int length) {
    text = new String(chars, start, length);
    parent = null;
  }

  public Text(String text) {
    this.text = text;
    parent = null;
  }

  // --- visiting ------------------------------------------------------

  @Override
  public Traversal accept(Visitor visitor) {
    return visitor.visitText(this);
  }

  // --- Structure -----------------------------------------------------

  public Element getParent() {
    return parent;
  }

  public void setParent(Element parent) {
    this.parent = parent;
  }

  // -------------------------------------------------------------------

  public String getText() {
    return text;
  }

  @Override
  public String toString() {
    return text;
  }

}
