package nl.knaw.huygens.tei;

/*
 * #%L
 * VisiTEI
 * =======
 * Copyright (C) 2011 - 2016 Huygens ING
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

public class TextLayer {

  private final StringBuilder builder;

  public TextLayer() {
    builder = new StringBuilder();
  }

  public int length() {
    return builder.length();
  }

  @Override
  public String toString() {
    return builder.toString();
  }

  // -------------------------------------------------------------------

  public void addLiteral(Object literal) {
    if (literal != null) {
      builder.append(literal.toString());
    }
  }

  public void addOpenTag(Element element) {
    if (element != null) {
      element.appendOpenTagTo(builder);
    }
  }

  public void addOpenTag(String elementName) {
    builder.append('<').append(elementName).append('>');
  }

  public void addCloseTag(Element element) {
    if (element != null) {
      element.appendCloseTagTo(builder);
    }
  }

  public void addCloseTag(String elementName) {
    builder.append('<').append('/').append(elementName).append('>');
  }

  public void addEmptyElementTag(Element element) {
    element.appendEmptyElementTagTo(builder);
  }

  public void addEmptyElementTag(String elementName) {
    builder.append('<').append(elementName).append('/').append('>');
  }

  public void addComment(String comment) {
    builder.append("<!-- ").append(comment).append(" -->");
  }

  public void addElement(Element element, Object content) {
    addOpenTag(element);
    addLiteral(content);
    addCloseTag(element);
  }

}
