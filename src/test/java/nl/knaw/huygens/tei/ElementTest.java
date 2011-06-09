package nl.knaw.huygens.tei;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ElementTest {

  private StringBuilder builder;

  @Before
  public void createBuilder() {
    builder = new StringBuilder();
  }

  private void assertRendered(String expected) {
    Assert.assertEquals(expected, builder.toString());
  }

  @Test
  public void noAttributes() {
    Element element = new Element("tagname");
    element.appendOpenTagTo(builder);
    element.appendCloseTagTo(builder);
    assertRendered("<tagname></tagname>");
  }

  @Test
  public void oneAttribute() {
    Element element = new Element("name", "key", "value");
    element.appendOpenTagTo(builder);
    assertRendered("<name key=\"value\">");
  }

  @Test
  public void twoAttributesInOrder() {
    Element element = new Element("name", "key1", "value1");
    element.setAttribute("key2", "value2");
    element.appendOpenTagTo(builder);
    assertRendered("<name key1=\"value1\" key2=\"value2\">");
  }

  @Test
  public void twoAttributesOutOfOrder() {
    Element element = new Element("name", "key2", "value2");
    element.setAttribute("key1", "value1");
    element.appendOpenTagTo(builder);
    assertRendered("<name key1=\"value1\" key2=\"value2\">");
  }

  @Test
  public void overwriteAttribute() {
    Element element = new Element("name", "key1", "value1");
    element.setAttribute("key1", "value2");
    element.appendOpenTagTo(builder);
    assertRendered("<name key1=\"value2\">");
  }

  @Test
  public void emptyElementTag() {
    Element element = new Element("name", "key", "value");
    element.appendEmptyElementTagTo(builder);
    assertRendered("<name key=\"value\"/>");
  }

  @Test
  public void testHasAttribute() {
    Assert.assertFalse(new Element("name").hasAttribute("key"));
    Assert.assertFalse(new Element("name", "key", "").hasAttribute("key"));
    Assert.assertFalse(new Element("name", "other", "value").hasAttribute("key"));
    Assert.assertTrue(new Element("name", "key", "value").hasAttribute("key"));
  }

  @Test
  public void testHasLanguage() {
    Assert.assertFalse(new Element("name").hasLanguage());
    Assert.assertFalse(new Element("name", "lang", "").hasLanguage());
    Assert.assertFalse(new Element("name", "key", "value").hasLanguage());
    Assert.assertTrue(new Element("name", "lang", "value").hasLanguage());
  }

  @Test
  public void testHasRendition() {
    Assert.assertFalse(new Element("name").hasRendition());
    Assert.assertFalse(new Element("name", Element.RENDITION, "").hasRendition());
    Assert.assertFalse(new Element("name", "other", "value").hasRendition());
    Assert.assertTrue(new Element("name", Element.RENDITION, "value").hasRendition());
  }

  @Test
  public void testHasType() {
    Assert.assertFalse(new Element("name", "key", "value").hasType("value"));
    Assert.assertFalse(new Element("name", Element.TYPE, "value").hasType("type"));
    Assert.assertTrue(new Element("name", Element.TYPE, "value").hasType("value"));
  }

  @Test
  public void testGeneratedElementHasNoLocation() {
    Element element = new Element("generated");
    Assert.assertEquals(-1, element.getStartLine());
    Assert.assertEquals(-1, element.getStartColumn());
  }

}
