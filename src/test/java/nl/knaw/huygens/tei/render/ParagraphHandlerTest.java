package nl.knaw.huygens.tei.render;

import nl.knaw.huygens.tei.DelegatingVisitor;
import nl.knaw.huygens.tei.Document;
import nl.knaw.huygens.tei.XmlContext;

import org.junit.Assert;
import org.junit.Test;

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
    Assert.assertEquals("text", process("<seg>text</seg>"));
  }

  @Test
  public void testOneParagraph() {
    Assert.assertEquals("<p>text</p>", process("<div><p>text</p></div>"));
  }

  @Test
  public void testTwoParagraph() {
    Assert.assertEquals("<p>text</p>\n<p>text</p>", process("<div><p>text</p>\n<p>text</p></div>"));
  }

  @Test
  public void testParagraphWithLanguage() {
    Assert.assertEquals("<p lang=\"la\">text</p>", process("<p lang=\"la\">text</p>"));
  }

}
