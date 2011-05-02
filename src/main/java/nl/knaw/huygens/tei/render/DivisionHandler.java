package nl.knaw.huygens.tei.render;

import nl.knaw.huygens.tei.Element;
import nl.knaw.huygens.tei.ElementHandler;
import nl.knaw.huygens.tei.Traversal;
import nl.knaw.huygens.tei.XmlContext;

/**
 * Renders the TEI <code>div</code> element.
 */
public class DivisionHandler implements ElementHandler<XmlContext> {

  @Override
  public Traversal enterElement(Element element, XmlContext context) {
    if (element.hasType()) {
      context.addOpenTag(Element.newDivElement(element.getType()));
    } else {
      context.addOpenTag(Element.DIV_TAG);
    }
    return Traversal.NEXT;
  }

  @Override
  public Traversal leaveElement(Element element, XmlContext context) {
    context.addCloseTag(Element.DIV_TAG);
    return Traversal.NEXT;
  }

}
