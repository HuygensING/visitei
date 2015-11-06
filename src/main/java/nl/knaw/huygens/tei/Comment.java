package nl.knaw.huygens.tei;

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

public class Comment extends Node {

  private String comment;
  private Element parent;

  public Comment(char[] chars, int start, int length) {
    comment = new String(chars, start, length);
    parent = null;
  }

  public Comment(String comment) {
    this.comment = comment;
    parent = null;
  }

  // --- visiting ------------------------------------------------------

  @Override
  public Traversal accept(Visitor visitor) {
    return visitor.visitComment(this);
  }

  // -------------------------------------------------------------------

  public Element getParent() {
    return parent;
  }

  public void setParent(Element parent) {
    this.parent = parent;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  @Override
  public String toString() {
    return comment;
  }

}