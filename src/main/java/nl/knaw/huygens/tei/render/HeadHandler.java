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
 * Renders the TEI <code>head</code> element.
 */
public class HeadHandler implements ElementHandler<XmlContext> {

  private static final String DEFAULT_TAG = "h3";

  private String tagName(Element element) {
    return element.getAttribute(Element.RENDITION, DEFAULT_TAG);
  }

  @Override
  public Traversal enterElement(Element element, XmlContext context) {
    Element rendered = new Element(tagName(element));
    rendered.copyAttributeFrom(element, Element.LANGUAGE);
    context.addOpenTag(rendered);
    return Traversal.NEXT;
  }

  @Override
  public Traversal leaveElement(Element element, XmlContext context) {
    context.addCloseTag(tagName(element));
    return Traversal.NEXT;
  }

}
