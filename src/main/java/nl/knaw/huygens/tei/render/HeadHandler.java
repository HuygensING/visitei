package nl.knaw.huygens.tei.render;

import nl.knaw.huygens.tei.Element;
import nl.knaw.huygens.tei.ElementHandler;
import nl.knaw.huygens.tei.Traversal;
import nl.knaw.huygens.tei.XmlContext;

/**
 * Renders the TEI <code>head</code> element:
 * <ul>
 * <li>if the parent is a letter div the element is not rendered;</li>
 * <li>other elements are rendered;</li>
 * <li>the "lang" attribute is transferred.</li>
 * </ul>
 */
public class HeadHandler implements ElementHandler<XmlContext> {

  private static final String DEFAULT_TAG = "h3";

  private boolean isLetterDiv(Element element) {
    return element.hasName("div") && element.hasType("letter");
  }

  private boolean isLetterHead(Element element) {
    Element parent = element.getParent();
    return (parent != null) && isLetterDiv(parent);
  }

  private String tagName(Element element) {
    return element.getAttribute(Element.RENDITION, DEFAULT_TAG);
  }

  @Override
  public Traversal enterElement(Element element, XmlContext context) {
    if (isLetterHead(element)) {
      return Traversal.STOP;
    } else {
      Element rendered = new Element(tagName(element));
      rendered.copyAttributeFrom(element, Element.LANGUAGE);
      context.addOpenTag(rendered);
      return Traversal.NEXT;
    }
  }

  @Override
  public Traversal leaveElement(Element element, XmlContext context) {
    if (!isLetterHead(element)) {
      context.addCloseTag(tagName(element));
    }
    return Traversal.NEXT;
  }

}
