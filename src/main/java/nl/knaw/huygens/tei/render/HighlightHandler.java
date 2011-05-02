package nl.knaw.huygens.tei.render;

import nl.knaw.huygens.tei.Element;
import nl.knaw.huygens.tei.ElementHandler;
import nl.knaw.huygens.tei.Traversal;
import nl.knaw.huygens.tei.XmlContext;

/**
 * Renders the TEI <code>hi</code> element.
 */
public class HighlightHandler implements ElementHandler<XmlContext> {

  @Override
  public Traversal enterElement(Element element, XmlContext context) {
    if (element.hasRendition()) {
      context.addOpenTag(Element.newSpanElement(element.getRendition()));
    }
    return Traversal.NEXT;
  }

  @Override
  public Traversal leaveElement(Element element, XmlContext context) {
    if (element.hasRendition()) {
      context.addCloseTag(Element.SPAN_TAG);
    }
    return Traversal.NEXT;
  }

}
