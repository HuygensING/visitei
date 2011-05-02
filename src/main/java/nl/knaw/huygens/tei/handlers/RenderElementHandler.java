package nl.knaw.huygens.tei.handlers;

import nl.knaw.huygens.tei.Element;
import nl.knaw.huygens.tei.ElementHandler;
import nl.knaw.huygens.tei.Traversal;
import nl.knaw.huygens.tei.XmlContext;

/**
 * Element handler that renders elements and their attributes as is.
 */
public class RenderElementHandler implements ElementHandler<XmlContext> {

  @Override
  public Traversal enterElement(Element element, XmlContext context) {
    if (element.hasChildren()) {
      context.addOpenTag(element);
    } else {
      context.addEmptyElementTag(element);
    }
    return Traversal.NEXT;
  }

  @Override
  public Traversal leaveElement(Element element, XmlContext context) {
    if (element.hasChildren()) {
      context.addCloseTag(element);
    }
    return Traversal.NEXT;
  }

}
