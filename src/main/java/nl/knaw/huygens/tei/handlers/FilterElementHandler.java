package nl.knaw.huygens.tei.handlers;

import nl.knaw.huygens.tei.Element;
import nl.knaw.huygens.tei.ElementHandler;
import nl.knaw.huygens.tei.Traversal;
import nl.knaw.huygens.tei.XmlContext;

/**
 * Ignores an element and its children.
 */
public class FilterElementHandler implements ElementHandler<XmlContext> {

  @Override
  public Traversal enterElement(Element element, XmlContext context) {
    return Traversal.STOP;
  }

  @Override
  public Traversal leaveElement(Element element, XmlContext context) {
    return Traversal.NEXT;
  }

}
