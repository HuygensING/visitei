package nl.knaw.huygens.tei

import javax.xml.xpath.XPathExpressionException
import kotlin.test.*
import org.junit.Test

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

class QueryableDocumentTest {
    @Test
    @Throws(XPathExpressionException::class)
    fun testXPathEvaluationToString() {
        val xml = """<root><x><name>
<x/><name/></name></x><text>hello
 world</text></root>"""
        val document = QueryableDocument.createFromXml(xml, true)
        //    assertEquals(false, document.needsPrefix());
        val result = document.evaluateXPathToString("//text")
        assertEquals("hello\n world", result)
    }

    @Test
    @Throws(XPathExpressionException::class)
    fun testXPathEvaluationToBoolean() {
        val xml = """<root><x><name>
<x/><name/></name></x><text>hello
 world</text></root>"""
        val document = QueryableDocument.createFromXml(xml, true)
        //    assertEquals(false, document.needsPrefix());
        val result = document.evaluateXPathToBoolean("boolean(//text)")
        assertTrue(result)
    }

    @Test
    @Throws(XPathExpressionException::class)
    fun testXPathWithNamespacesEvaluationToString() {
        val xml =
            "<a xmlns=\"http://example.com/ns/default\" xmlns:ns=\"http://example.com/ns/default2\"><b>1</b><ns:b>2</ns:b></a>"
        val document = QueryableDocument.createFromXml(xml, true)
        val result = document.evaluateXPathToString("//b")
        assertEquals("1", result)
        val result2 = document.evaluateXPathToString("//ns:b")
        assertEquals("2", result2)
    }

    @Test
    @Throws(XPathExpressionException::class)
    fun testXPathWithNamespacesEvaluationToString1() {
        val xml = "<a xmlns=\"http://example.com/ns/default\"><b>1</b><b>2</b></a>"
        val document = QueryableDocument.createFromXml(xml, true)
        var result = document.evaluateXPathToString("//b[1]")
        assertEquals("1", result)
        result = document.evaluateXPathToString("//b[2]")
        assertEquals("2", result)
    }

    @Test
    @Throws(XPathExpressionException::class)
    fun testXPathEvaluationToDouble() {
        val xml = """<root><x><name>
<x/><name/></name></x><text>hello
 world</text></root>"""
        val document = QueryableDocument.createFromXml(xml, true)
        val result = document.evaluateXPathToDouble("count(//x)")
        assertTrue(result == 2.0)
    }

    @Test
    @Throws(XPathExpressionException::class)
    fun testXPathEvaluationToW3CNodeList() {
        val xml = "<root><b xml:id=\"1\">een</b><b xml:id=\"2\">twee</b></root>"
        val document = QueryableDocument.createFromXml(xml, true)
        val list = document.evaluateXPathToW3CNodeList("//b")
        assertTrue(list.length == 2)
        assertEquals("een", list.item(0).textContent)
        assertEquals("twee", list.item(1).textContent)
    } //  @Test
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
