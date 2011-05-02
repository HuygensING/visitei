package nl.knaw.huygens.tei.render;

import nl.knaw.huygens.tei.Element;
import nl.knaw.huygens.tei.ElementHandler;
import nl.knaw.huygens.tei.Traversal;
import nl.knaw.huygens.tei.XmlContext;

/**
 * Renders the TEI <code>seg</code> element.
 */
public class SegmentHandler implements ElementHandler<XmlContext> {

  @Override
  public Traversal enterElement(Element element, XmlContext context) {
    if (element.hasType()) {
      context.addOpenTag(Element.newSpanElement(element.getType()));
    }
    return Traversal.NEXT;
  }

  @Override
  public Traversal leaveElement(Element element, XmlContext context) {
    if (element.hasType()) {
      context.addCloseTag(Element.SPAN_TAG);
    }
    return Traversal.NEXT;
  }

}
