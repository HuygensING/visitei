package nl.knaw.huygens.tei.render;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import nl.knaw.huygens.tei.DelegatingVisitor;
import nl.knaw.huygens.tei.Document;
import nl.knaw.huygens.tei.XmlContext;

public class ParagraphHandlerTest {

  private String process(String xml) {
    Document document = Document.createFromXml(xml);
    DelegatingVisitor<XmlContext> visitor = new DelegatingVisitor<XmlContext>(new XmlContext());
    visitor.addElementHandler(new ParagraphHandler(), "p");
    document.accept(visitor);
    return visitor.getContext().getResult();
  }

  @Test
  public void testNoParagraph() {
    assertEquals("text", process("<seg>text</seg>"));
  }

  @Test
  public void testOneParagraph() {
    assertEquals("<p>text</p>", process("<div><p>text</p></div>"));
  }

  @Test
  public void testTwoParagraph() {
    assertEquals("<p>text</p>\n<p>text</p>", process("<div><p>text</p>\n<p>text</p></div>"));
  }

  @Test
  public void testParagraphWithLanguage() {
    assertEquals("<p lang=\"la\">text</p>", process("<p lang=\"la\">text</p>"));
  }

}
