package nl.knaw.huygens.tei.render;

import nl.knaw.huygens.tei.Element;
import nl.knaw.huygens.tei.ElementHandler;
import nl.knaw.huygens.tei.Traversal;
import nl.knaw.huygens.tei.XmlContext;

/**
 * Renders the TEI <code>figure</code> element.
 */
public class FigureHandler implements ElementHandler<XmlContext> {

  private static final Element FIGURE_DIV = Element.newDivElement("figure");
  private static final Element FIGURE_SPAN = Element.newSpanElement("figure");

  private Element renderedElement(Element element) {
    return element.hasRendition("inline") ? FIGURE_SPAN : FIGURE_DIV;
  }

  @Override
  public Traversal enterElement(Element element, XmlContext context) {
    context.addOpenTag(renderedElement(element));
    return Traversal.NEXT;
  }

  @Override
  public Traversal leaveElement(Element element, XmlContext context) {
    context.addCloseTag(renderedElement(element));
    return Traversal.NEXT;
  }

}
