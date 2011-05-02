package nl.knaw.huygens.tei.render;

import nl.knaw.huygens.tei.DelegatingVisitor;
import nl.knaw.huygens.tei.Document;
import nl.knaw.huygens.tei.XmlContext;

import org.junit.Assert;
import org.junit.Test;

public class DivisionHandlerTest {

  private String process(String xml) {
    Document document = Document.createFromXml(xml);
    DelegatingVisitor<XmlContext> visitor = new DelegatingVisitor<XmlContext>(new XmlContext());
    visitor.addElementHandler(new DivisionHandler(), "div");
    document.accept(visitor);
    return visitor.getContext().getResult();
  }

  @Test
  public void testNoDiv() {
    Assert.assertEquals("text", process("<span>text</span>"));
  }

  @Test
  public void testDivWithoutType() {
    Assert.assertEquals("<div>text</div>", process("<div>text</div>"));
  }

  @Test
  public void testDivWithType() {
    Assert.assertEquals("<div class=\"x\">text</div>", process("<div type=\"x\">text</div>"));
  }

}
