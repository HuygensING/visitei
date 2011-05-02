package nl.knaw.huygens.tei;

/**
 * A visitor that merely traverses the document tree.
 */
public class DefaultVisitor implements Visitor {

  @Override
  public Traversal enterDocument(Document document) {
    return Traversal.NEXT;
  }

  @Override
  public Traversal leaveDocument(Document document) {
    return Traversal.NEXT;
  }

  @Override
  public Traversal enterElement(Element element) {
    return Traversal.NEXT;
  }

  @Override
  public Traversal leaveElement(Element element) {
    return Traversal.NEXT;
  }

  @Override
  public Traversal visitText(Text text) {
    return Traversal.NEXT;
  }

}
