package nl.knaw.huygens.tei.render;

import nl.knaw.huygens.tei.DelegatingVisitor;
import nl.knaw.huygens.tei.Document;
import nl.knaw.huygens.tei.XmlContext;

import org.junit.Assert;
import org.junit.Test;

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
    Assert.assertEquals("text", process("<hi>text</hi>"));
  }

  @Test
  public void testSegmentWithoutType() {
    Assert.assertEquals("text", process("<seg>text</seg>"));
  }

  @Test
  public void testSegmentWithRend() {
    Assert.assertEquals("<span class=\"x\">text</span>", process("<seg type=\"x\">text</seg>"));
  }

}
