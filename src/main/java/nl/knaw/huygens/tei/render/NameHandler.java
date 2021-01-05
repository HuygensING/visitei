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
