package nl.knaw.huygens.tei.render;

/*
 * #%L
 * VisiTEI
 * =======
 * Copyright (C) 2011 - 2015 Huygens ING
 * =======
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

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
