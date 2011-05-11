package nl.knaw.huygens.tei.demo;

import java.io.File;

import nl.knaw.huygens.tei.DelegatingVisitor;
import nl.knaw.huygens.tei.Document;
import nl.knaw.huygens.tei.Element;
import nl.knaw.huygens.tei.ElementHandler;
import nl.knaw.huygens.tei.Traversal;
import nl.knaw.huygens.tei.XmlContext;
import nl.knaw.huygens.tei.export.ExportVisitor;
import nl.knaw.huygens.tei.export.Files;

/*
 * Demonstrates the use of a visitor for assigning an attribute to elements
 * in a TEI document. Note the difference in the output for the paragraph
 * containing a seg element:
 * <p length="1559"><seg type="incipit">Magno me ...
 * <p length="1482"><seg length="77" type="incipit">Magno me ...
 */
public class LengthAssigner {

  public static void main(String[] args) {
    File file = new File("data/test/grotius/a0001.xml");
    String xml = Files.readTextFromFile(file);

    System.out.println("--------------------");
    process(xml, "p");
    System.out.println("--------------------");
    process(xml, "p", "seg");
    System.out.println("--------------------");
  }

  private static void process(String xml, String... names) {
    Document document = Document.createFromXml(xml);
    LengthVisitor lengthVisitor = new LengthVisitor(names);
    document.accept(lengthVisitor);
    ExportVisitor exportVisitor = new ExportVisitor();
    document.accept(exportVisitor);
    System.out.println(exportVisitor.getContext().getResult());
  }

  /**
   * Visitor that uses ElementHandler for specified elements.
   */
  private static class LengthVisitor extends DelegatingVisitor<XmlContext> {
    public LengthVisitor(String... elementNames) {
      super(new XmlContext());
      addElementHandler(new LengthHandler(), elementNames);
    }
  }

  /**
   * Handler that assignes text length to elements.
   */
  private static class LengthHandler implements ElementHandler<XmlContext> {
    @Override
    public Traversal enterElement(Element element, XmlContext context) {
      context.openLayer();
      return Traversal.NEXT;
    }

    @Override
    public Traversal leaveElement(Element element, XmlContext context) {
      String text = context.closeLayer();
      String length = Integer.toString(text.length());
      element.setAttribute("length", length);
      return Traversal.NEXT;
    }
  }

}
