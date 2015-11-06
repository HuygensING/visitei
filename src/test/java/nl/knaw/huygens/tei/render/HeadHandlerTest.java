package nl.knaw.huygens.tei.render;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import nl.knaw.huygens.tei.DelegatingVisitor;
import nl.knaw.huygens.tei.Document;
import nl.knaw.huygens.tei.XmlContext;

public class HeadHandlerTest {

  private String process(String xml) {
    Document document = Document.createFromXml(xml);
    DelegatingVisitor<XmlContext> visitor = new DelegatingVisitor<XmlContext>(new XmlContext());
    visitor.addElementHandler(new HeadHandler(), "head");
    document.accept(visitor);
    return visitor.getContext().getResult();
  }

  @Test
  public void testHeadWithoutRend() {
    assertEquals("<h3>text</h3>", process("<div type=\"section\"><head>text</head></div>"));
  }

  @Test
  public void testHeadWithRend() {
    assertEquals("<h4>text</h4>", process("<div type=\"section\"><head rend=\"h4\">text</head></div>"));
  }

  @Test
  public void testHeadWithLang() {
    assertEquals("<h3 lang=\"la\">text</h3>", process("<div><head lang=\"la\">text</head></div>"));
  }

}
