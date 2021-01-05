package nl.knaw.huygens.tei;

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

/**
 * Ancestor for document, elements and text in the DOM.
 */
public abstract class Node {

  public static final int UNSPECIFIED = -1;

  public abstract Traversal accept(Visitor visitor);

  // locator info
  private int startLine = UNSPECIFIED;
  private int startColumn = UNSPECIFIED;
  private int endLine = UNSPECIFIED;
  private int endColumn = UNSPECIFIED;

  protected void setStartLine(int lineNumber) {
    startLine = lineNumber;
  }

  public int getStartLine() {
    return startLine;
  }

  protected void setStartColumn(int columnNumber) {
    startColumn = columnNumber;
  }

  public int getStartColumn() {
    return startColumn;
  }

  protected void setEndLine(int lineNumber) {
    endLine = lineNumber;
  }

  public int getEndLine() {
    return endLine;
  }

  protected void setEndColumn(int columnNumber) {
    endColumn = columnNumber;
  }

  public int getEndColumn() {
    return endColumn;
  }

}
