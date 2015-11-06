package nl.knaw.huygens.tei.render;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import nl.knaw.huygens.tei.DelegatingVisitor;
import nl.knaw.huygens.tei.Document;
import nl.knaw.huygens.tei.XmlContext;

public class TableCellHandlerTest {

  private String process(String xml) {
    Document document = Document.createFromXml(xml);
    DelegatingVisitor<XmlContext> visitor = new DelegatingVisitor<XmlContext>(new XmlContext());
    visitor.addElementHandler(new TableCellHandler(), "cell");
    document.accept(visitor);
    return visitor.getContext().getResult();
  }

  @Test
  public void testNoCell() {
    assertEquals("text", process("<seg>text</seg>"));
  }

  @Test
  public void testEmptyCell() {
    assertEquals("<td>&nbsp;</td>", process("<cell></cell>"));
  }

  @Test
  public void testHeaderCell() {
    assertEquals("<th>text</th>", process("<cell role=\"header\">text</cell>"));
  }

  @Test
  public void testMultiColumnCell() {
    assertEquals("<td colspan=\"2\">text</td>", process("<cell cols=\"2\">text</cell>"));
  }

  @Test
  public void testMultiColumnHeaderCell() {
    assertEquals("<th colspan=\"3\">text</th>", process("<cell role=\"header\" cols=\"3\">text</cell>"));
  }

}
