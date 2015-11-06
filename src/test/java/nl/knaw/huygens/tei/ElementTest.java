package nl.knaw.huygens.tei;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class ElementTest {

  private StringBuilder builder;

  @Before
  public void createBuilder() {
    builder = new StringBuilder();
  }

  private void assertRendered(String expected) {
    assertEquals(expected, builder.toString());
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
    assertRendered("<name key2=\"value2\" key1=\"value1\">");
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
    assertFalse(new Element("name").hasAttribute("key"));
    assertFalse(new Element("name", "key", "").hasAttribute("key"));
    assertFalse(new Element("name", "other", "value").hasAttribute("key"));
    assertTrue(new Element("name", "key", "value").hasAttribute("key"));
  }

  @Test
  public void testHasLanguage() {
    assertFalse(new Element("name").hasLanguage());
    assertFalse(new Element("name", "lang", "").hasLanguage());
    assertFalse(new Element("name", "key", "value").hasLanguage());
    assertTrue(new Element("name", "lang", "value").hasLanguage());
  }

  @Test
  public void testSetLanguage() {
    Element element = new Element("name");
    assertEquals("", element.getLanguage());
    element.setLanguage("fr");
    assertEquals("fr", element.getLanguage());
    element.setLanguage("la");
    assertEquals("la", element.getLanguage());
  }

  @Test
  public void testHasRendition() {
    assertFalse(new Element("name").hasRendition());
    assertFalse(new Element("name", Element.RENDITION, "").hasRendition());
    assertFalse(new Element("name", "other", "value").hasRendition());
    assertTrue(new Element("name", Element.RENDITION, "value").hasRendition());
  }

  @Test
  public void testHasType() {
    assertFalse(new Element("name", "key", "value").hasType("value"));
    assertFalse(new Element("name", Element.TYPE, "value").hasType("type"));
    assertTrue(new Element("name", Element.TYPE, "value").hasType("value"));
  }

  @Test
  public void testGeneratedElementHasNoLocation() {
    Element element = new Element("generated");
    assertEquals(-1, element.getStartLine());
    assertEquals(-1, element.getStartColumn());
  }

  @Test
  public void testMissingIntAttribute() {
    Element element = new Element("name", "key", "37");
    assertEquals(42, element.getIntAttribute("missing", 42));
  }

  @Test
  public void testInvalidIntAttribute() {
    Element element = new Element("name", "key", "text");
    assertEquals(42, element.getIntAttribute("key", 42));
  }

  @Test
  public void testValidIntAttribute() {
    Element element = new Element("name", "key", "37");
    assertEquals(37, element.getIntAttribute("key", 42));
  }

  @Test
  public void testMissingDoubleAttribute() {
    Element element = new Element("name", "key", "33.3");
    assertEquals(42.0, element.getDoubleAttribute("missing", 42.0), 0.001);
  }

  @Test
  public void testInvalidDoubleAttribute() {
    Element element = new Element("name", "key", "text");
    assertEquals(42.0, element.getDoubleAttribute("key", 42.0), 0.001);
  }

  @Test
  public void testValidDoubleAttribute() {
    Element element = new Element("name", "key", "33.3");
    assertEquals(33.3, element.getDoubleAttribute("key", 42.0), 0.001);
  }

  @Test
  public void testGetAttributeNames() {
    Element element = new Element("name", "key1", "value1");
    element.setAttribute("key2", "value2");
    Set<String> attributeNames = element.getAttributeNames();
    assertEquals(2, attributeNames.size());
    assertTrue(attributeNames.contains("key1"));
    assertTrue(attributeNames.contains("key2"));
  }

  @Test
  public void testHasParentWithName() {
    Element element = new Element("name");
    assertFalse(element.hasParentWithName("parent"));
    Element parent = new Element("name");
    element.setParent(parent);
    assertFalse(element.hasParentWithName("parent"));
    parent = new Element("parent");
    element.setParent(parent);
    assertTrue(element.hasParentWithName("parent"));
  }

}
