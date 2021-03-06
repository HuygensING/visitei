package nl.knaw.huygens.tei;

/*
 * #%L
 * VisiTEI
 * =======
 * Copyright (C) 2011 - 2017 Huygens ING
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

import java.util.List;

import org.junit.Test;

public class DocumentTest {

  @Test(expected = RuntimeException.class)
  public void testEmptyFile() {
    Document.createFromXml("", false);
  }

  private void testGetElementsByTagName(String xml, String name, int expected) {
    Document document = Document.createFromXml(xml, false);
    List<Element> elements = document.getElementsByTagName(name);
    assertEquals(expected, elements.size());
    for (Element element : elements) {
      assertEquals(name, element.getName());
    }
  }

  @Test
  public void noElements() {
    testGetElementsByTagName("<root></root>", "name", 0);
  }

  @Test
  public void otherElement() {
    testGetElementsByTagName("<root><x/></root>", "name", 0);
  }

  @Test
  public void elementAtDepth1() {
    testGetElementsByTagName("<root><name/></root>", "name", 1);
  }

  @Test
  public void elementAtDepth2() {
    testGetElementsByTagName("<root><x><name/></x></root>", "name", 1);
  }

  @Test
  public void twoElementsAtDepth1() {
    testGetElementsByTagName("<root><x/><name/><x/><name/><x/></root>", "name", 2);
  }

  @Test
  public void twoElementsAtDepth1And2() {
    testGetElementsByTagName("<root><x><name/></x><name/></root>", "name", 2);
  }

  @Test
  public void twoNestedElements() {
    testGetElementsByTagName("<root><x><name><x/><name/></name></x></root>", "name", 2);
  }

  @Test
  public void testNameSpace1() {
    String xml = "<root><div xml:id=\"A\">a</div></root>";
    testGetElementsByTagName(xml, "div", 1);
    Document document = Document.createFromXml(xml, true);
    List<Element> elements = document.getElementsByTagName("div");
    Element element = elements.get(0);
    assertEquals("", element.getAttribute("id"));
    assertEquals("A", element.getAttribute("xml:id"));
  }

  @Test
  public void testNameSpace2() {
    String xml = "<root xmlns:my=\"http://whatever.com/\"><div xml:id=\"xml\" id=\"id\" my:id=\"my\">a</div></root>";
    Document document = Document.createFromXml(xml, true);
    List<Element> elements = document.getElementsByTagName("div");
    Element element = elements.get(0);
    assertEquals("id", element.getAttribute("id"));
    assertEquals("xml", element.getAttribute("xml:id"));
    assertEquals("my", element.getAttribute("my:id"));

    // document = Document.createFromXml(xml);
    // elements = document.getElementsByTagName("div");
    // element = elements.get(0);
    // assertEquals("xml", element.getAttribute("xml:id"));
    // assertEquals("my", element.getAttribute("my:id"));
    // assertEquals("id", element.getAttribute("id"));

  }

  @Test
  public void testNameSpace3() {
    String xml = "<root xmlns:my=\"http://whatever.com/\"><div xml:id=\"xml\" id=\"id\" my:id=\"my\">a</div></root>";
    Document document = Document.createFromXml(xml, true);
    List<Element> elements = document.getElementsByTagName("root");
    assertEquals(1, elements.size());
    Element element = elements.get(0);
    assertEquals("http://whatever.com/", element.getAttribute("xmlns:my"));
  }

  @Test
  public void testElementPositions() {
    String xml = "<root><x><name>\n" + //
        "<x/><name/></name></x><text>hello\n" + //
        " world</text></root>";
    Document document = Document.createFromXml(xml, true);
    List<Element> elements = document.getElementsByTagName("name");

    Element first = elements.get(0);
    assertEquals(1, first.getStartLine());
    assertEquals(16, first.getStartColumn());
    assertEquals(2, first.getEndLine());
    assertEquals(19, first.getEndColumn());

    Element second = elements.get(1);
    assertEquals(2, second.getStartLine());
    assertEquals(12, second.getStartColumn());
    assertEquals(2, second.getEndLine());
    assertEquals(12, second.getEndColumn());

    assertEquals(1, document.getStartLine());
    assertEquals(1, document.getStartColumn());
    assertEquals(3, document.getEndLine());
    assertEquals(21, document.getEndColumn());
  }

  // ---------------------------------------------------------------------------

  private void testGetElementsByTagNameAndAttribute(String xml, String name, String key, String value, int expected) {
    Document document = Document.createFromXml(xml, false);
    List<Element> elements = document.getElementsByNameAndAttribute(name, key, value);
    assertEquals(expected, elements.size());
    for (Element element : elements) {
      assertEquals(name, element.getName());
      assertEquals(value, element.getAttribute(key));
    }
  }

  @Test
  public void findNoElement() {
    testGetElementsByTagNameAndAttribute("<root></root>", "name", "key", "value", 0);
  }

  @Test
  public void findOneElement() {
    testGetElementsByTagNameAndAttribute("<root><name key=\"value\"/><name key=\"value2\"/></root>", "name", "key", "value", 1);
  }

  @Test
  public void findTwoElements() {
    testGetElementsByTagNameAndAttribute("<root><name key=\"value\"><name key=\"value\"/><name key=\"value2\"/></name></root>", "name", "key", "value", 2);
  }

}
