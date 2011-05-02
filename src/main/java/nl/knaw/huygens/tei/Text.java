package nl.knaw.huygens.tei;

public class Text extends Node {

  private final String text;

  public Text(char[] chars, int start, int length) {
    text = new String(chars, start, length);
  }

  public Text(String text) {
    this.text = text;
  }

  // --- visiting ------------------------------------------------------

  @Override
  public Traversal accept(Visitor visitor) {
    return visitor.visitText(this);
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
