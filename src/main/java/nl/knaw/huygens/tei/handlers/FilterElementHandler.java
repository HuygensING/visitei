package nl.knaw.huygens.tei.handlers;

import nl.knaw.huygens.tei.Context;
import nl.knaw.huygens.tei.Element;
import nl.knaw.huygens.tei.ElementHandler;
import nl.knaw.huygens.tei.Traversal;

/**
 * Ignores an element and its children.
 */
public class FilterElementHandler<T extends Context> implements ElementHandler<T> {

  @Override
  public Traversal enterElement(Element element, T context) {
    return Traversal.STOP;
  }

  @Override
  public Traversal leaveElement(Element element, T context) {
    return Traversal.NEXT;
  }

}
