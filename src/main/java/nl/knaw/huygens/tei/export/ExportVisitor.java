package nl.knaw.huygens.tei.export;

/*
 * #%L
 * VisiTEI
 * =======
 * Copyright (C) 2011 - 2017 Huygens ING
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

import nl.knaw.huygens.tei.DelegatingVisitor;
import nl.knaw.huygens.tei.XmlContext;
import nl.knaw.huygens.tei.handlers.RenderTextHandler;
import nl.knaw.huygens.tei.handlers.RenderElementHandler;

/**
 * Converts a DOM to xml.
 *
 * Notes:
 * - attributes of elements may be reordered.
 * - the internal format of Java strings uses (Unix) line endings '\n',
 *   hence the exported format also uses these.
 * - the result may differ in whitespace after the close of the root element.
 * - only three entities are handled.
 * - xml comments are preserved.
 */
public class ExportVisitor extends DelegatingVisitor<XmlContext> {

  public ExportVisitor() {
    super(new XmlContext());
    setDefaultElementHandler(new RenderElementHandler());
    setTextHandler(new RenderTextHandler<XmlContext>() {
      @Override
      protected String filterText(String text) {
        int n = text.length();
        StringBuilder builder = new StringBuilder((int) (n * 1.1));
        for (int i = 0; i < n; i++) {
          char c = text.charAt(i);
          switch (c) {
          case '<':
            builder.append("&lt;");
            break;
          case '>':
            builder.append("&gt;");
            break;
          case '&':
            builder.append("&amp;");
            break;
          default:
            builder.append(c);
            break;
          }
        }
        return builder.toString();
      }
    });
  }

}
