package nl.knaw.huygens.tei.handlers;

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

import nl.knaw.huygens.tei.Context;

public class XmlTextHandler<T extends Context> extends RenderTextHandler<T> {

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
    //    return StringEscapeUtils.escapeXml(text)
  }
}