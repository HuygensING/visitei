package nl.knaw.huygens.tei;

import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class DocumentFactoryTest {

  @Test
  public void testXMLNSAttributesAreHandledCorrectly() {
    String xml = "<TEI xmlns=\"http://www.tei-c.org/ns/1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.tei-c.org/ns/1.0 http://www.tei-c.org/release/xml/tei/custom/schema/xsd/tei_all.xsd\"></TEI>";
    Document doc = Document.createFromXml(xml, true);
    List<Element> elements = doc.getElementsByTagName("TEI");
    Assert.assertEquals(elements.size(), 1);
    Element tei = elements.get(0);
    Set<String> attributeNames = tei.getAttributeNames();
    Assert.assertTrue(attributeNames.contains("xmlns:xsi"));
    Assert.assertTrue(attributeNames.contains("xsi:schemaLocation"));
    Assert.assertTrue(attributeNames.contains("xmlns"));
  }

}
