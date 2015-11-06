package nl.knaw.huygens.tei.render;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import nl.knaw.huygens.tei.DelegatingVisitor;
import nl.knaw.huygens.tei.Document;
import nl.knaw.huygens.tei.XmlContext;

public class SegmentHandlerTest {

  private String process(String xml) {
    Document document = Document.createFromXml(xml);
    DelegatingVisitor<XmlContext> visitor = new DelegatingVisitor<XmlContext>(new XmlContext());
    visitor.addElementHandler(new SegmentHandler(), "seg");
    document.accept(visitor);
    return visitor.getContext().getResult();
  }

  @Test
  public void testNoSegment() {
    assertEquals("text", process("<hi>text</hi>"));
  }

  @Test
  public void testSegmentWithoutType() {
    assertEquals("text", process("<seg>text</seg>"));
  }

  @Test
  public void testSegmentWithRend() {
    assertEquals("<span class=\"x\">text</span>", process("<seg type=\"x\">text</seg>"));
  }

}
