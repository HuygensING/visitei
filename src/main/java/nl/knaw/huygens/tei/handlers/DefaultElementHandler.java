package nl.knaw.huygens.tei.handlers;

import nl.knaw.huygens.tei.Context;
import nl.knaw.huygens.tei.Element;
import nl.knaw.huygens.tei.ElementHandler;
import nl.knaw.huygens.tei.Traversal;

/**
 * Handles element silently.
 */
public class DefaultElementHandler<T extends Context> implements ElementHandler<T> {

  public DefaultElementHandler() {}

  @Override
  public Traversal enterElement(Element element, T context) {
    return Traversal.NEXT;
  }

  @Override
  public Traversal leaveElement(Element element, T context) {
    return Traversal.NEXT;
  }

}
