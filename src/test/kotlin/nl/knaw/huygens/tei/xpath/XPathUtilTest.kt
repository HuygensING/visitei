package nl.knaw.huygens.tei.xpath

import javax.xml.xpath.XPathExpressionException
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.Test
import nl.knaw.huygens.tei.QueryableDocument

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

class XPathUtilTest {
    @Test
    fun `test namespace extraction`() {
        val xml =
            """<a xmlns="http://ns.example.com/whatever" xmlns:a="http://ns.example.com/whatever/a">whaterver, <a:person>dude</a:person></a>"""
        val namespaceInfo = XPathUtil.getNamespaceInfo(xml)
        assertTrue(namespaceInfo.entries.size == 2)
        assertTrue(namespaceInfo.containsKey("a"))
//        Log.info("namespaces = {}", namespaceInfo)
    }

    @Test
    @Throws(XPathExpressionException::class)
    fun `test document xPath evaluation`() {
        val xml =
            """<a xmlns="http://ns.example.com/whatever" xmlns:a="http://ns.example.com/whatever/a">whaterver, <a:person>dude</a:person></a>"""
        val doc = QueryableDocument.createFromXml(xml, true)
        val result = doc.evaluateXPathToString("//a:person")
        assertEquals("dude", result)
    }

    @Test
    @Throws(XPathExpressionException::class)
    fun `test document xPath evaluation with xml id`() {
        val xml = """<a>whatever, <div xml:id="d0">dude</div></a>"""
        val doc = QueryableDocument.createFromXml(xml, true)
        val result = doc.evaluateXPathToString("//div[@xml:id='d0']")
        assertEquals("dude", result)
    }
}
