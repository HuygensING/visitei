package nl.knaw.huygens.tei

import kotlin.test.assertEquals
import org.junit.Test

/*
* #%L
 * VisiTEI
 * =======
 * Copyright (C) 2011 - 2024 Huygens ING
 * =======
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public
 *  License along with this program.  If not, see
 *  <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
*/

class DocumentTest {
    @Test(expected = RuntimeException::class)
    fun testEmptyFile() {
        Document.createFromXml("", false)
    }

    @Test
    fun noElements() {
        testGetElementsByTagName("<root></root>", "name", 0)
    }

    @Test
    fun otherElement() {
        testGetElementsByTagName("<root><x/></root>", "name", 0)
    }

    @Test
    fun elementAtDepth1() {
        testGetElementsByTagName("<root><name/></root>", "name", 1)
    }

    @Test
    fun elementAtDepth2() {
        testGetElementsByTagName("<root><x><name/></x></root>", "name", 1)
    }

    @Test
    fun twoElementsAtDepth1() {
        testGetElementsByTagName("<root><x/><name/><x/><name/><x/></root>", "name", 2)
    }

    @Test
    fun twoElementsAtDepth1And2() {
        testGetElementsByTagName("<root><x><name/></x><name/></root>", "name", 2)
    }

    @Test
    fun twoNestedElements() {
        testGetElementsByTagName("<root><x><name><x/><name/></name></x></root>", "name", 2)
    }

    @Test
    fun testNameSpace1() {
        val xml = """<root><div xml:id="A">a</div></root>"""
        testGetElementsByTagName(xml, "div", 1)
        val document = Document.createFromXml(xml, true)
        val elements = document.getElementsByTagName("div")
        val element = elements[0]
        assertEquals("", element.getAttribute("id"))
        assertEquals("A", element.getAttribute("xml:id"))
    }

    @Test
    fun testNameSpace2() {
        val xml = """<root xmlns:my="http://whatever.com/"><div xml:id="xml" id="id" my:id="my">a</div></root>"""
        val document = Document.createFromXml(xml, true)
        val elements = document.getElementsByTagName("div")
        val element = elements[0]
        assertEquals("id", element.getAttribute("id"))
        assertEquals("xml", element.getAttribute("xml:id"))
        assertEquals("my", element.getAttribute("my:id"))

        // document = Document.createFromXml(xml);
        // elements = document.getElementsByTagName("div");
        // element = elements.get(0);
        // assertEquals("xml", element.getAttribute("xml:id"));
        // assertEquals("my", element.getAttribute("my:id"));
        // assertEquals("id", element.getAttribute("id"));
    }

    @Test
    fun testNameSpace3() {
        val xml = """<root xmlns:my="http://whatever.com/"><div xml:id="xml" id="id" my:id="my">a</div></root>"""
        val document = Document.createFromXml(xml, true)
        val elements = document.getElementsByTagName("root")
        assertEquals(1, elements.size.toLong())
        val element = elements[0]
        assertEquals("http://whatever.com/", element.getAttribute("xmlns:my"))
    }

    @Test
    fun testElementPositions() {
        val xml = """<root><x><name>
<x/><name/></name></x><text>hello
 world</text></root>"""
        val document = Document.createFromXml(xml, true)
        val elements = document.getElementsByTagName("name")

        val first = elements[0]
        assertEquals(1, first.startLine.toLong())
        assertEquals(16, first.startColumn.toLong())
        assertEquals(2, first.endLine.toLong())
        assertEquals(19, first.endColumn.toLong())

        val second = elements[1]
        assertEquals(2, second.startLine.toLong())
        assertEquals(12, second.startColumn.toLong())
        assertEquals(2, second.endLine.toLong())
        assertEquals(12, second.endColumn.toLong())

        assertEquals(1, document.startLine.toLong())
        assertEquals(1, document.startColumn.toLong())
        assertEquals(3, document.endLine.toLong())
        assertEquals(21, document.endColumn.toLong())
    }

    @Test
    fun findNoElement() {
        testGetElementsByTagNameAndAttribute("<root></root>", "name", "key", "value", 0)
    }

    @Test
    fun findOneElement() {
        testGetElementsByTagNameAndAttribute(
            """<root><name key="value"/><name key="value2"/></root>""",
            "name",
            "key",
            "value",
            1
        )
    }

    @Test
    fun findTwoElements() {
        testGetElementsByTagNameAndAttribute(
            """<root><name key="value"><name key="value"/><name key="value2"/></name></root>""",
            "name",
            "key",
            "value",
            2
        )
    }

    companion object {
        private fun testGetElementsByTagName(xml: String, name: String, expected: Int) {
            val document = Document.createFromXml(xml, false)
            val elements = document.getElementsByTagName(name)
            assertEquals(expected.toLong(), elements.size.toLong())
            for (element in elements) {
                assertEquals(name, element.name)
            }
        }

        private fun testGetElementsByTagNameAndAttribute(
            xml: String,
            name: String,
            key: String,
            value: String,
            expected: Int
        ) {
            val document = Document.createFromXml(xml, false)
            val elements = document.getElementsByNameAndAttribute(name, key, value)
            assertEquals(expected.toLong(), elements.size.toLong())
            for (element in elements) {
                assertEquals(name, element.name)
                assertEquals(value, element.getAttribute(key))
            }
        }
    }
}
