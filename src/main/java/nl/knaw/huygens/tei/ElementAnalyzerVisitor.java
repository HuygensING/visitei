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

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * Visitor for analyzing the elements used in a document.
 */
public class ElementAnalyzerVisitor extends DefaultVisitor {

  private static class Count {
    String name;
    int count;

    public Count(String name) {
      this.name = name;
      count = 0;
    }
  }

  private final Map<String, Count> counts;

  public ElementAnalyzerVisitor() {
    counts = Maps.newTreeMap();
  }

  public void register(String name) {
    Count item = counts.get(name);
    if (item == null) {
      item = new Count(name);
      counts.put(name, item);
    }
    item.count++;
  }

  public void displayCounts() {
    for (Count count : counts.values()) {
      System.out.printf("%-20s %10d%n", count.name, count.count);
    }
  }

  @Override
  public Traversal enterElement(Element element) {
    if (element.hasType()) {
      register(element.getName() + "-t:" + element.getType());
    } else if (element.hasRendition()) {
      register(element.getName() + "-r:" + element.getRendition());
    } else {
      register(element.getName());
    }
    return Traversal.NEXT;
  }

}
