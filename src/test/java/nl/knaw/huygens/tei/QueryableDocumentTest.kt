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
import static org.junit.Assert.assertTrue;

import javax.xml.xpath.XPathExpressionException;

import org.junit.Test;
import org.w3c.dom.NodeList;

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

  @Test
  public void testXPathEvaluationToDouble() throws XPathExpressionException {
    String xml = "<root><x><name>\n" + //
        "<x/><name/></name></x><text>hello\n" + //
        " world</text></root>";
    QueryableDocument document = QueryableDocument.createFromXml(xml, true);
    Double result = document.evaluateXPathToDouble("count(//x)");
    assertTrue(result == 2);
  }

  @Test
  public void testXPathEvaluationToW3CNodeList() throws XPathExpressionException {
    String xml = "<root><b xml:id=\"1\">een</b><b xml:id=\"2\">twee</b></root>";
    QueryableDocument document = QueryableDocument.createFromXml(xml, true);
    NodeList list = document.evaluateXPathToW3CNodeList("//b");
    assertTrue(list.getLength() == 2);
    assertEquals("een", list.item(0).getTextContent());
    assertEquals("twee", list.item(1).getTextContent());
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
