package nl.knaw.huygens.tei;

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

import java.util.Collection;

public class TeiUtils {

  /**
   * Returns the nearest ancestor with the specified name,
   * {@code null} if no such an element exists.
   */
  public static Element getAncestor(Element element, String name) {
    element = element.getParent();
    while (element != null) {
      if (element.hasName(name)) {
        return element;
      }
      element = element.getParent();
    }
    return null;
  }

  /**
   * Returns {@code true} if the specified element has an ancestor
   * with the specified name, {@code false} otherwise.
   */
  public static boolean hasAncestor(Element element, String name) {
    return getAncestor(element, name) != null;
  }

  private TeiUtils() {
    throw new AssertionError("Non-instantiable class");
  }


  /**
   * Returns {@code true} if the specified element has an ancestor
   * with one of the specified names, {@code false} otherwise.
   */
  public static boolean hasAncestor(Element element, Collection<String> names) {
    element = element.getParent();
    while (element != null) {
      if (names.contains(element.getName())) {
        return true;
      }
      element = element.getParent();
    }
    return false;
  }

}
