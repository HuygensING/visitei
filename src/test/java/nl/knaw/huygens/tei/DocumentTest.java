package nl.knaw.huygens.tei;

import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.Test;

public class DocumentTest {

  @Test(expected = RuntimeException.class)
  public void testEmptyFile() {
    Document.createFromXml("");
  }

  private void testGetElementsByTagName(String xml, String name, int expected) {
    Document document = Document.createFromXml(xml);
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
    Document document = Document.createFromXml(xml);
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

}
