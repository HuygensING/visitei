package nl.knaw.huygens.tei.render;

import nl.knaw.huygens.tei.Element;
import nl.knaw.huygens.tei.ElementHandler;
import nl.knaw.huygens.tei.Traversal;
import nl.knaw.huygens.tei.XmlContext;

/*
 * We support TEI lists with the following structure:
 * <list>
 *   <label>label-text</label> <item>item-text</item>
 *   <label>label-text</label> <item>item-text</item>
 * </list>
 * with the label-element optional.
 * The "lang" attribute of the list is transferred.
 */
public class ListHandler implements ElementHandler<XmlContext> {

  private static final String LIST = "ul";
  private static final Element ITEM = new Element("li");
  private static final Element LABEL = Element.newSpanElement("listLabel");

  private String labelText;

  @Override
  public Traversal enterElement(Element element, XmlContext context) {
    if (element.hasName("list")) {
      Element rendered = new Element(LIST);
      rendered.copyAttributeFrom(element, Element.LANGUAGE);
      context.addOpenTag(rendered);
      labelText = null;
    } else if (element.hasName("item")) {
      context.addOpenTag(ITEM);
      if (labelText != null) {
        context.addElement(LABEL, labelText);
        labelText = null;
      }
    } else if (element.hasName("label")) {
      context.openLayer();
    }
    return Traversal.NEXT;
  }

  @Override
  public Traversal leaveElement(Element element, XmlContext context) {
    if (element.hasName("list")) {
      context.addCloseTag(LIST);
    } else if (element.hasName("item")) {
      context.addCloseTag(ITEM);
    } else if (element.hasName("label")) {
      labelText = context.closeLayer();
    }
    return Traversal.NEXT;
  }

}
