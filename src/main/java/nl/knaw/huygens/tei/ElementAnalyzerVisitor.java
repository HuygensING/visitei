package nl.knaw.huygens.tei;

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
