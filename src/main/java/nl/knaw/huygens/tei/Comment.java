package nl.knaw.huygens.tei;

public class Comment extends Node {

  private String comment;
  private Element parent;

  public Comment(char[] chars, int start, int length) {
    comment = new String(chars, start, length);
    parent = null;
  }

  public Comment(String comment) {
    this.comment = comment;
    parent = null;
  }

  // --- visiting ------------------------------------------------------

  @Override
  public Traversal accept(Visitor visitor) {
    return visitor.visitComment(this);
  }

  // -------------------------------------------------------------------

  public Element getParent() {
    return parent;
  }

  public void setParent(Element parent) {
    this.parent = parent;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  @Override
  public String toString() {
    return comment;
  }

}
