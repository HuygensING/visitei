package nl.knaw.huygens.tei.render;

import nl.knaw.huygens.tei.Element;
import nl.knaw.huygens.tei.ElementHandler;
import nl.knaw.huygens.tei.Traversal;
import nl.knaw.huygens.tei.XmlContext;

/**
 * Renders the TEI <code>seg</code> element.
 */
public class NameHandler implements ElementHandler<XmlContext> {

  private static final Element NAME_SPAN = Element.newSpanElement("name");

  private boolean isPerson(Element element) {
    return element.hasType("person");
  }

  @Override
  public Traversal enterElement(Element element, XmlContext context) {
    if (isPerson(element)) {
      context.addOpenTag(NAME_SPAN);
    }
    return Traversal.NEXT;
  }

  @Override
  public Traversal leaveElement(Element element, XmlContext context) {
    if (isPerson(element)) {
      context.addCloseTag(NAME_SPAN);
    }
    return Traversal.NEXT;
  }

}
