package nl.knaw.huygens.tei.render;

import nl.knaw.huygens.tei.DelegatingVisitor;
import nl.knaw.huygens.tei.Document;
import nl.knaw.huygens.tei.XmlContext;

import org.junit.Assert;
import org.junit.Test;

public class GraphicHandlerTest {

  private static class TestResolver implements UrlResolver {
    @Override
    public String resolve(String key) {
      return "base/" + key;
    }
  }

  private String process(String xml) {
    Document document = Document.createFromXml(xml);
    DelegatingVisitor<XmlContext> visitor = new DelegatingVisitor<XmlContext>(new XmlContext());
    visitor.addElementHandler(new GraphicHandler(new TestResolver()), "graphic");
    document.accept(visitor);
    return visitor.getContext().getResult();
  }

  @Test
  public void testNoGraphic() {
    Assert.assertEquals("text", process("<seg>text</seg>"));
  }

  @Test
  public void testGraphicWithoutUrl() {
    Assert.assertEquals("", process("<graphic id=\"test.gif\"/>"));
  }

  @Test
  public void testGraphic() {
    // N.B. Order of attributes depends on implementation
    Assert.assertEquals("<img alt=\"[test.gif]\" src=\"base/test.gif\"/>", process("<graphic url=\"test.gif\"/>"));
  }

  @Test
  public void testGraphicWithContent() {
    // N.B. Order of attributes depends on implementation
    Assert.assertEquals("<img alt=\"[test.gif]\" src=\"base/test.gif\"/>", process("<graphic url=\"test.gif\">text</graphic>"));
  }

}
