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
import nl.knaw.huygens.tei.Traversal;
import nl.knaw.huygens.tei.XmlContext;
import nl.knaw.huygens.tei.handlers.DefaultElementHandler;

/**
 * Renders the TEI <code>graphic</code> element.
 */
public class GraphicHandler extends DefaultElementHandler<XmlContext> {

  private UrlResolver urlResolver;

  public GraphicHandler(UrlResolver resolver) {
    urlResolver = resolver;
  }

  @Override
  public Traversal enterElement(Element element, XmlContext context) {
    String url = element.getAttribute("url");
    if (url.length() != 0) {
      Element rendered = new Element("img");
      rendered.setAttribute("src", urlResolver.resolve(url));
      rendered.setAttribute("alt", "[" + url + "]");
      context.addEmptyElementTag(rendered);
    }
    return Traversal.STOP;
  }

}
