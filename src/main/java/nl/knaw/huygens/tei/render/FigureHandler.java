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
