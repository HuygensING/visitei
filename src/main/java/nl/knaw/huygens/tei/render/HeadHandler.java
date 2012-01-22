package nl.knaw.huygens.tei.render;

import nl.knaw.huygens.tei.Element;
import nl.knaw.huygens.tei.ElementHandler;
import nl.knaw.huygens.tei.Traversal;
import nl.knaw.huygens.tei.XmlContext;

/**
 * Renders the TEI <code>head</code> element.
 */
public class HeadHandler implements ElementHandler<XmlContext> {

  private static final String DEFAULT_TAG = "h3";

  private String tagName(Element element) {
    return element.getAttribute(Element.RENDITION, DEFAULT_TAG);
  }

  @Override
  public Traversal enterElement(Element element, XmlContext context) {
    Element rendered = new Element(tagName(element));
    rendered.copyAttributeFrom(element, Element.LANGUAGE);
    context.addOpenTag(rendered);
    return Traversal.NEXT;
  }

  @Override
  public Traversal leaveElement(Element element, XmlContext context) {
    context.addCloseTag(tagName(element));
    return Traversal.NEXT;
  }

}
