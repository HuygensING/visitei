package nl.knaw.huygens.tei.render;

import nl.knaw.huygens.tei.Element;
import nl.knaw.huygens.tei.ElementHandler;
import nl.knaw.huygens.tei.Traversal;
import nl.knaw.huygens.tei.XmlContext;

/**
 * Renders the <code>lg</code> element, used for poems.
 */
public class LineGroupHandler implements ElementHandler<XmlContext> {

  private int level;

  public LineGroupHandler() {
    level = 0;
  }

  private boolean isPoem(Element element) {
    return element.hasName("lg") && element.hasType("poem");
  }

  @Override
  public Traversal enterElement(Element element, XmlContext context) {
    if (isPoem(element)) {
      Element rendered = Element.newDivElement("poem");
      rendered.copyAttributeFrom(element, Element.LANGUAGE);
      context.addOpenTag(rendered);
      level++;
    }
    return Traversal.NEXT;
  }

  @Override
  public Traversal leaveElement(Element element, XmlContext context) {
    if (element.hasName("l") && level != 0) {
      context.addEmptyElementTag("br");
    } else if (isPoem(element)) {
      context.addCloseTag(Element.DIV_TAG);
      level--;
    }
    return Traversal.NEXT;
  }

}
