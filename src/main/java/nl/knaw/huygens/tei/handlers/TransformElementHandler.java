package nl.knaw.huygens.tei.handlers;

import nl.knaw.huygens.tei.Element;
import nl.knaw.huygens.tei.ElementHandler;
import nl.knaw.huygens.tei.Traversal;
import nl.knaw.huygens.tei.XmlContext;

/**
 * Handler that replaces an element with another, fixed element.
 */
public class TransformElementHandler implements ElementHandler<XmlContext> {

  private final Element renderedElement;

  public TransformElementHandler(Element element) {
    renderedElement = element;
  }

  public TransformElementHandler(String name) {
    renderedElement = new Element(name);
  }

  @Override
  public Traversal enterElement(Element element, XmlContext context) {
    if (element.hasChildren()) {
      context.addOpenTag(renderedElement);
    } else {
      context.addEmptyElementTag(renderedElement);
    }
    return Traversal.NEXT;
  }

  @Override
  public Traversal leaveElement(Element element, XmlContext context) {
    if (element.hasChildren()) {
      context.addCloseTag(renderedElement);
    }
    return Traversal.NEXT;
  }

}
