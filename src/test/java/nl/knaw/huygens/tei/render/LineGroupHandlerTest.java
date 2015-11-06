package nl.knaw.huygens.tei.render;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import nl.knaw.huygens.tei.DelegatingVisitor;
import nl.knaw.huygens.tei.Document;
import nl.knaw.huygens.tei.XmlContext;

public class LineGroupHandlerTest {

  private String process(String xml) {
    Document document = Document.createFromXml(xml);
    DelegatingVisitor<XmlContext> visitor = new DelegatingVisitor<XmlContext>(new XmlContext());
    visitor.addElementHandler(new LineGroupHandler(), "lg", "l");
    document.accept(visitor);
    return visitor.getContext().getResult();
  }

  @Test
  public void testNoLineGroup() {
    assertEquals("text", process("<seg>text</seg>"));
  }

  @Test
  public void testLineGroupNoPoem() {
    assertEquals("12", process("<lg><l>1</l><l>2</l></lg>"));
  }

  @Test
  public void testLineGroupPoem() {
    assertEquals("<div class=\"poem\">1<br/>2<br/></div>", process("<lg type=\"poem\"><l>1</l><l>2</l></lg>"));
  }

}
