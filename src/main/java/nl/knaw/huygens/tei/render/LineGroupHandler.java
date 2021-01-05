package nl.knaw.huygens.tei.render;

/*
 * #%L
 * VisiTEI
 * =======
 * Copyright (C) 2011 - 2021 Huygens ING
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
