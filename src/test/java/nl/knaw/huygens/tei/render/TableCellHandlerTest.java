package nl.knaw.huygens.tei.render;

import nl.knaw.huygens.tei.DelegatingVisitor;
import nl.knaw.huygens.tei.Document;
import nl.knaw.huygens.tei.XmlContext;

import org.junit.Assert;
import org.junit.Test;

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
    Assert.assertEquals("text", process("<seg>text</seg>"));
  }

  @Test
  public void testEmptyCell() {
    Assert.assertEquals("<td>&nbsp;</td>", process("<cell></cell>"));
  }

  @Test
  public void testHeaderCell() {
    Assert.assertEquals("<th>text</th>", process("<cell role=\"header\">text</cell>"));
  }

  @Test
  public void testMultiColumnCell() {
    Assert.assertEquals("<td colspan=\"2\">text</td>", process("<cell cols=\"2\">text</cell>"));
  }

  @Test
  public void testMultiColumnHeaderCell() {
    Assert.assertEquals("<th colspan=\"3\">text</th>", process("<cell role=\"header\" cols=\"3\">text</cell>"));
  }

}
