package nl.knaw.huygens.tei.render;

import nl.knaw.huygens.tei.Element;
import nl.knaw.huygens.tei.ElementHandler;
import nl.knaw.huygens.tei.Traversal;
import nl.knaw.huygens.tei.XmlContext;

public class TableCellHandler implements ElementHandler<XmlContext> {

  private String getTagName(Element element) {
    return element.hasAttribute("role", "header") ? "th" : "td";
  }

  @Override
  public Traversal enterElement(Element element, XmlContext context) {
    Element rendered = new Element(getTagName(element));
    if (element.hasAttribute("cols")) {
      rendered.setAttribute("colspan", element.getAttribute("cols"));
    }
    if (element.hasChildren()) {
      context.addOpenTag(rendered);
    } else {
      context.addElement(rendered, "&nbsp;");
    }
    return Traversal.NEXT;
  }

  @Override
  public Traversal leaveElement(Element element, XmlContext context) {
    if (element.hasChildren()) {
      context.addCloseTag(getTagName(element));
    }
    return Traversal.NEXT;
  }

}
