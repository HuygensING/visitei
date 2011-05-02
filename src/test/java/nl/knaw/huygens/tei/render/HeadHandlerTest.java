package nl.knaw.huygens.tei.render;

import nl.knaw.huygens.tei.DelegatingVisitor;
import nl.knaw.huygens.tei.Document;
import nl.knaw.huygens.tei.XmlContext;

import org.junit.Assert;
import org.junit.Test;

public class HeadHandlerTest {

  private String process(String xml) {
    Document document = Document.createFromXml(xml);
    DelegatingVisitor<XmlContext> visitor = new DelegatingVisitor<XmlContext>(new XmlContext());
    visitor.addElementHandler(new HeadHandler(), "head");
    document.accept(visitor);
    return visitor.getContext().getResult();
  }

  @Test
  public void testNoLetterHeadWithoutRend() {
    Assert.assertEquals("<h3>text</h3>", process("<div type=\"section\"><head>text</head></div>"));
  }

  @Test
  public void testNoLetterHeadWithRend() {
    Assert.assertEquals("<h4>text</h4>", process("<div type=\"section\"><head rend=\"h4\">text</head></div>"));
  }

  @Test
  public void testLetterHead() {
    Assert.assertEquals("", process("<div type=\"letter\"><head>text</head></div>"));
  }

  @Test
  public void testLetterHeadWithExtra() {
    Assert.assertEquals("xyz", process("<div type=\"letter\"><p>xyz</p><head>text</head></div>"));
  }

  @Test
  public void testOtherHead() {
    Assert.assertEquals("<h3>text</h3>", process("<div type=\"letter\"><div><head>text</head></div></div>"));
  }

}
