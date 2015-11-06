package nl.knaw.huygens.tei.export;

import static org.junit.Assert.assertEquals;
import java.io.File;
import org.junit.Test;
import nl.knaw.huygens.tei.Document;

public class ExportVisitorTest {

  private String process(String xml) {
    Document document = Document.createFromXml(xml);
    ExportVisitor visitor = new ExportVisitor();
    document.accept(visitor);
    return visitor.getContext().getResult();
  }

  @Test
  public void testBasicXml() {
    String xml = "<TEI><text><head><h1>Hello World</h1></head></text></TEI>";
    assertEquals(xml, process(xml));
  }

  @Test
  public void testEntities() {
    String xml = "<TEI><text>ge&lt;daen&gt;, &amp;c.</text></TEI>";
    assertEquals(xml, process(xml));
  }

  @Test
  public void testComplexXml() {
    // File has UNIX style EOL's.
    File file = new File("data/test/grotius/a0001.xml");
    String xml = Files.readTextFromFile(file).trim();
    // Normalize order of attributes
    String normalized = process(xml);
    assertEquals(xml.length(), normalized.length());
    // Normalized xml should be idempotent
    assertEquals(normalized, process(normalized));
  }

}
