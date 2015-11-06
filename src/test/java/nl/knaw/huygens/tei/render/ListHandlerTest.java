package nl.knaw.huygens.tei.render;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import nl.knaw.huygens.tei.DelegatingVisitor;
import nl.knaw.huygens.tei.Document;
import nl.knaw.huygens.tei.XmlContext;

public class ListHandlerTest {

  private String process(String xml) {
    Document document = Document.createFromXml(xml);
    DelegatingVisitor<XmlContext> visitor = new DelegatingVisitor<XmlContext>(new XmlContext());
    visitor.addElementHandler(new ListHandler(), "list", "item", "label");
    document.accept(visitor);
    return visitor.getContext().getResult();
  }

  @Test
  public void testNoList() {
    assertEquals("text", process("<seg>text</seg>"));
  }

  @Test
  public void testListWithoutLabels() {
    assertEquals("<ul><li>a</li><li>b</li></ul>", process("<list><item>a</item><item>b</item></list>"));
  }

  @Test
  public void testListWithLabels() {
    String xml = "<list><label>1.</label><item>a</item><label>2.</label><item>b</item></list>";
    String rendered = "<ul><li><span class=\"listLabel\">1.</span>a</li><li><span class=\"listLabel\">2.</span>b</li></ul>";
    assertEquals(rendered, process(xml));
  }

}
