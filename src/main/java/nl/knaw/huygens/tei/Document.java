package nl.knaw.huygens.tei;

import java.util.List;

import com.google.common.collect.Lists;

public class Document extends Node {
  private Element root;

  public static Document createFromXml(String xml) {
    return new DocumentFactory(xml, false).getDocument();
  }

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
