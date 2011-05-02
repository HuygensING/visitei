package nl.knaw.huygens.tei;

public interface ElementHandler<T extends Context> {

  Traversal enterElement(Element element, T context);

  Traversal leaveElement(Element element, T context);

}
