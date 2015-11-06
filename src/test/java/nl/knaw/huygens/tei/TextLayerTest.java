package nl.knaw.huygens.tei;

import static org.junit.Assert.assertEquals;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class TextLayerTest {

  private TextLayer layer;

  @Before
  public void setupTextStream() {
    layer = new TextLayer();
  }

  @Test
  public void testEmpty() {
    assertEquals(0, layer.length());
    assertEquals("", layer.toString());
  }

  @Test
  public void testNullLiteral() {
    layer.addLiteral(null);
    assertEquals(0, layer.length());
    assertEquals("", layer.toString());
  }

  @Test
  public void testIntLiteral() {
    layer.addLiteral(314159);
    assertEquals(6, layer.length());
    assertEquals("314159", layer.toString());
  }

  @Test
  public void testStringLiteral() {
    layer.addLiteral("xyz");
    assertEquals(3, layer.length());
    assertEquals("xyz", layer.toString());
  }

  @Test
  public void testOpenTagNoAttributes() {
    layer.addOpenTag("tag");
    assertEquals("<tag>", layer.toString());
  }

  @Test
  public void testOpenTagOneAttribute() {
    layer.addOpenTag(new Element("tag", "key", "value"));
    assertEquals("<tag key=\"value\">", layer.toString());
  }

  @Test
  public void testOpenTagTwoAttributes() {
    Map<String, String> attributes = XmlUtils.newAttributes();
    attributes.put("key1", "value1");
    attributes.put("key2", "value2");
    layer.addOpenTag(new Element("tag", attributes));
    assertEquals("<tag key1=\"value1\" key2=\"value2\">", layer.toString());
  }

  @Test
  public void testOpenTagWithAmpsersand() {
    layer.addOpenTag(new Element("ref", "target", "http://www.host.nl/nnbw?source=1&page_number=11"));
    assertEquals("<ref target=\"http://www.host.nl/nnbw?source=1&amp;page_number=11\">", layer.toString());
  }

  @Test
  public void testCloseTagByName() {
    layer.addCloseTag("tag");
    assertEquals("</tag>", layer.toString());
  }

  @Test
  public void testCloseTagByElement() {
    layer.addCloseTag(new Element("tag"));
    assertEquals("</tag>", layer.toString());
  }

  @Test
  public void testSequence() {
    layer.addOpenTag("h1");
    layer.addLiteral("II");
    layer.addCloseTag("h1");
    layer.addOpenTag(new Element("p", "id", "p2.0"));
    layer.addLiteral("Er wordt gesproken over de \"staatsfabriek\".");
    layer.addCloseTag("p");
    assertEquals("<h1>II</h1><p id=\"p2.0\">Er wordt gesproken over de \"staatsfabriek\".</p>", layer.toString());
  }

}
