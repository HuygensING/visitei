package nl.knaw.huygens.tei;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.xml.xpath.XPathExpressionException;

import org.junit.Test;

public class QueryableDocumentTest {
  @Test
  public void testXPathEvaluationToString() throws XPathExpressionException {
    String xml = "<root><x><name>\n" + //
        "<x/><name/></name></x><text>hello\n" + //
        " world</text></root>";
    QueryableDocument document = QueryableDocument.createFromXml(xml, true);
    //    assertEquals(false, document.needsPrefix());
    String result = document.evaluateXPathToString("//text");
    assertEquals("hello\n world", result);
  }

  @Test
  public void testXPathEvaluationToBoolean() throws XPathExpressionException {
    String xml = "<root><x><name>\n" + //
        "<x/><name/></name></x><text>hello\n" + //
        " world</text></root>";
    QueryableDocument document = QueryableDocument.createFromXml(xml, true);
    //    assertEquals(false, document.needsPrefix());
    Boolean result = document.evaluateXPathToBoolean("boolean(//text)");
    assertTrue(result);
  }

  @Test
  public void testXPathWithNamespacesEvaluationToString() throws XPathExpressionException {
    String xml = "<a xmlns=\"http://example.com/ns/default\" xmlns:ns=\"http://example.com/ns/default2\"><b>1</b><ns:b>2</ns:b></a>";
    QueryableDocument document = QueryableDocument.createFromXml(xml, true);
    String result = document.evaluateXPathToString("//b");
    assertEquals("1", result);
    String result2 = document.evaluateXPathToString("//ns:b");
    assertEquals("2", result2);
  }

  @Test
  public void testXPathWithNamespacesEvaluationToString1() throws XPathExpressionException {
    String xml = "<a xmlns=\"http://example.com/ns/default\"><b>1</b><b>2</b></a>";
    QueryableDocument document = QueryableDocument.createFromXml(xml, true);
    String result = document.evaluateXPathToString("//b[1]");
    assertEquals("1", result);
    result = document.evaluateXPathToString("//b[2]");
    assertEquals("2", result);
  }

  //  @Test
  //  public void testXPathEvaluationToNode() throws XPathExpressionException {
  //    String xml = "<root>"//
  //        + "<a id=\"A\">aaa</a>"//
  //        + "<b id=\"B\">bbb</b>"//
  //        + "</root>";
  //    QueryableDocument document = QueryableDocument.createFromXml(xml, true);
  //    org.w3c.dom.Node result = document.evaluateXPathToNode("//*[@id='A']");
  //    assertEquals("a", result.getNodeName());
  //    assertEquals("aaa", result.getFirstChild().getTextContent());
  //  }

}
