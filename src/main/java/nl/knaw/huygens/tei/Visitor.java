package nl.knaw.huygens.tei;

/**
 * Hierarchical visitor.
 */
public interface Visitor {

  Traversal enterDocument(Document document);

  Traversal leaveDocument(Document document);

  Traversal enterElement(Element element);

  Traversal leaveElement(Element element);

  Traversal visitText(Text text);

}
