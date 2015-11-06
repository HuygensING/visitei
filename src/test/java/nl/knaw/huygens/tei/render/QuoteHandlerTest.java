package nl.knaw.huygens.tei.render;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import nl.knaw.huygens.tei.DelegatingVisitor;
import nl.knaw.huygens.tei.Document;
import nl.knaw.huygens.tei.XmlContext;

public class QuoteHandlerTest {

  private String process(String xml) {
    Document document = Document.createFromXml(xml);
    DelegatingVisitor<XmlContext> visitor = new DelegatingVisitor<XmlContext>(new XmlContext());
    visitor.addElementHandler(new QuoteHandler(), "q");
    document.accept(visitor);
    return visitor.getContext().getResult();
  }

  @Test
  public void testNoQuote() {
    assertEquals("text", process("<seg>text</seg>"));
  }

  @Test
  public void testPlainQuote() {
    assertEquals("&#8216;text&#8217;", process("<q>text</q>"));
  }

  @Test
  public void testOtherQuote() {
    assertEquals("&#8216;text&#8217;", process("<q rend=\"x\">text</q>"));
  }

  @Test
  public void testBlockQuote() {
    assertEquals("<div class=\"blockquote\">text</div>", process("<q rend=\"bq\">text</q>"));
  }

}
