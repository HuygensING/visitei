package nl.knaw.huygens.tei;

/**
 * Ancestor for document, elements and text in the DOM.
 */
public abstract class Node {

  public static final int UNSPECIFIED = -1;

  public abstract Traversal accept(Visitor visitor);

  // locator info
  private int startLine = UNSPECIFIED;
  private int startColumn = UNSPECIFIED;
  private int endLine = UNSPECIFIED;
  private int endColumn = UNSPECIFIED;

  protected void setStartLine(int lineNumber) {
    startLine = lineNumber;
  }

  public int getStartLine() {
    return startLine;
  }

  protected void setStartColumn(int columnNumber) {
    startColumn = columnNumber;
  }

  public int getStartColumn() {
    return startColumn;
  }

  protected void setEndLine(int lineNumber) {
    endLine = lineNumber;
  }

  public int getEndLine() {
    return endLine;
  }

  protected void setEndColumn(int columnNumber) {
    endColumn = columnNumber;
  }

  public int getEndColumn() {
    return endColumn;
  }

}
