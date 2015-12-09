package nl.knaw.huygens.tei;

/*
 * #%L
 * VisiTEI
 * =======
 * Copyright (C) 2011 - 2015 Huygens ING
 * =======
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

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
    Element element = new Element("name").withAttribute("key", "value");
    element.appendOpenTagTo(builder);
    assertRendered("<name key=\"value\">");
  }

  @Test
  public void twoAttributesInOrder() {
    Element element = new Element("name")//
        .withAttribute("key1", "value1")//
        .withAttribute("key2", "value2");
    element.appendOpenTagTo(builder);
    assertRendered("<name key1=\"value1\" key2=\"value2\">");
  }

  @Test
  public void twoAttributesOutOfOrder() {
    Element element = new Element("name")//
        .withAttribute("key2", "value2")//
        .withAttribute("key1", "value1");
    element.appendOpenTagTo(builder);
    assertRendered("<name key2=\"value2\" key1=\"value1\">");
  }

  @Test
  public void overwriteAttribute() {
    Element element = new Element("name")//
        .withAttribute("key1", "value1")//
        .withAttribute("key1", "value2");
    element.appendOpenTagTo(builder);
    assertRendered("<name key1=\"value2\">");
  }

  @Test
  public void emptyElementTag() {
    Element element = new Element("name").withAttribute("key", "value");
    element.appendEmptyElementTagTo(builder);
    assertRendered("<name key=\"value\"/>");
  }

  @Test
  public void testHasAttribute() {
    assertFalse(new Element("name").hasAttribute("key"));
    assertFalse(new Element("name").withAttribute("key", "").hasAttribute("key"));
    assertFalse(new Element("name").withAttribute("other", "value").hasAttribute("key"));
    assertTrue(new Element("name").withAttribute("key", "value").hasAttribute("key"));
  }

  @Test
  public void testHasLanguage() {
    assertFalse(new Element("name").hasLanguage());
    assertFalse(new Element("name").withAttribute("lang", "").hasLanguage());
    assertFalse(new Element("name").withAttribute("key", "value").hasLanguage());
    assertTrue(new Element("name").withAttribute("lang", "value").hasLanguage());
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
    assertFalse(new Element("name").withAttribute(Element.RENDITION, "").hasRendition());
    assertFalse(new Element("name").withAttribute("other", "value").hasRendition());
    assertTrue(new Element("name").withAttribute(Element.RENDITION, "value").hasRendition());
  }

  @Test
  public void testHasType() {
    assertFalse(new Element("name").withAttribute("key", "value").hasType("value"));
    assertFalse(new Element("name").withAttribute(Element.TYPE, "value").hasType("type"));
    assertTrue(new Element("name").withAttribute(Element.TYPE, "value").hasType("value"));
  }

  @Test
  public void testGeneratedElementHasNoLocation() {
    Element element = new Element("generated");
    assertEquals(-1, element.getStartLine());
    assertEquals(-1, element.getStartColumn());
  }

  @Test
  public void testMissingIntAttribute() {
    Element element = new Element("name").withAttribute("key", "37");
    assertEquals(42, element.getIntAttribute("missing", 42));
  }

  @Test
  public void testInvalidIntAttribute() {
    Element element = new Element("name").withAttribute("key", "text");
    assertEquals(42, element.getIntAttribute("key", 42));
  }

  @Test
  public void testValidIntAttribute() {
    Element element = new Element("name").withAttribute("key", "37");
    assertEquals(37, element.getIntAttribute("key", 42));
  }

  @Test
  public void testMissingDoubleAttribute() {
    Element element = new Element("name").withAttribute("key", "33.3");
    assertEquals(42.0, element.getDoubleAttribute("missing", 42.0), 0.001);
  }

  @Test
  public void testInvalidDoubleAttribute() {
    Element element = new Element("name").withAttribute("key", "text");
    assertEquals(42.0, element.getDoubleAttribute("key", 42.0), 0.001);
  }

  @Test
  public void testValidDoubleAttribute() {
    Element element = new Element("name").withAttribute("key", "33.3");
    assertEquals(33.3, element.getDoubleAttribute("key", 42.0), 0.001);
  }

  @Test
  public void testGetAttributeNames() {
    Element element = new Element("name")//
        .withAttribute("key1", "value1")//
        .withAttribute("key2", "value2");
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
