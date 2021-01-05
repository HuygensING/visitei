package nl.knaw.huygens.tei;

/*
 * #%L
 * VisiTEI
 * =======
 * Copyright (C) 2011 - 2021 Huygens ING
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
    layer.addOpenTag(new Element("tag").withAttribute("key", "value"));
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
    layer.addOpenTag(new Element("ref").withAttribute("target", "http://www.host.nl/nnbw?source=1&page_number=11"));
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
    layer.addOpenTag(new Element("p").withAttribute("id", "p2.0"));
    layer.addLiteral("Er wordt gesproken over de \"staatsfabriek\".");
    layer.addCloseTag("p");
    assertEquals("<h1>II</h1><p id=\"p2.0\">Er wordt gesproken over de \"staatsfabriek\".</p>", layer.toString());
  }

}
