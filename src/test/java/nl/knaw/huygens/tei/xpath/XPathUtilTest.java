package nl.knaw.huygens.tei.xpath;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import org.junit.Test;

import nl.knaw.huygens.Log;
import nl.knaw.huygens.tei.QueryableDocument;

public class XPathUtilTest {

  @Test
  public void testNamespaceExtraction() {
    String xml = "<a xmlns=\"http://ns.example.com/whatever\" xmlns:a=\"http://ns.example.com/whatever/a\">whaterver, <a:person>dude</a:person></a>";
    Map<String, String> namespaceInfo = XPathUtil.getNamespaceInfo(xml);
    Log.info("namespaces = {}", namespaceInfo);
  }

  @Test
  public void testDocumentXPathEvaluation() throws XPathExpressionException {
    String xml = "<a xmlns=\"http://ns.example.com/whatever\" xmlns:a=\"http://ns.example.com/whatever/a\">whaterver, <a:person>dude</a:person></a>";
    QueryableDocument doc = QueryableDocument.createFromXml(xml, true);
    String result = doc.evaluateXPathToString("//a:person");
    assertEquals("dude", result);
  }

}
