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

import java.util.List;

import com.google.common.collect.Lists;

public class Document extends Node {
  private Element root;

  /**
   * @deprecated  use {@link #createFromXml(xml,preserveNamespacePrefix)} to explicitly indicate whether to preserve the namespace prefix for an Elements attributes
   */
  @Deprecated
  public static Document createFromXml(String xml) {
    return new DocumentFactory(xml, false).getDocument();
  }

  /**
   * 
   * @param xml  xml source
   * @param preserveNamespacePrefix preserve the namespace prefix for an Elements attributes
   * @return the Document
   */
  public static Document createFromXml(String xml, boolean preserveNamespacePrefix) {
    return new DocumentFactory(xml, preserveNamespacePrefix).getDocument();
  }

  public Document() {
    root = null;
  }

  public Element getRoot() {
    return root;
  }

  public void setRoot(Element element) {
    root = element;
  }

  /**
   * Returns a list of all elements with the specified tag name in the order
   * in which they are encountered in preorder traversal of the document tree.
   */
  public List<Element> getElementsByTagName(final String name) {
    final List<Element> elements = Lists.newArrayList();
    Visitor visitor = new DefaultVisitor() {
      @Override
      public Traversal enterElement(Element element) {
        if (element.hasName(name)) {
          elements.add(element);
        }
        return Traversal.NEXT;
      }
    };
    accept(visitor);
    return elements;
  }

  // --- Visiting ------------------------------------------------------

  @Override
  public Traversal accept(Visitor visitor) {
    if (visitor.enterDocument(this) == Traversal.NEXT) {
      if (root != null) {
        root.accept(visitor);
      }
    }
    return visitor.leaveDocument(this);
  }

}
