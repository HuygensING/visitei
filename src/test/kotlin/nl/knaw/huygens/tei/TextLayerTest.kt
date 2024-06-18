package nl.knaw.huygens.tei

import kotlin.test.assertEquals
import org.junit.Before
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

class TextLayerTest {
    private var layer: TextLayer? = null

    @Before
    fun setupTextStream() {
        layer = TextLayer()
    }

    @Test
    fun testEmpty() {
        assertEquals(0, layer!!.length().toLong())
        assertEquals("", layer.toString())
    }

    @Test
    fun testNullLiteral() {
        layer!!.addLiteral(null)
        assertEquals(0, layer!!.length().toLong())
        assertEquals("", layer.toString())
    }

    @Test
    fun testIntLiteral() {
        layer!!.addLiteral(314159)
        assertEquals(6, layer!!.length().toLong())
        assertEquals("314159", layer.toString())
    }

    @Test
    fun testStringLiteral() {
        layer!!.addLiteral("xyz")
        assertEquals(3, layer!!.length().toLong())
        assertEquals("xyz", layer.toString())
    }

    @Test
    fun testOpenTagNoAttributes() {
        layer!!.addOpenTag("tag")
        assertEquals("<tag>", layer.toString())
    }

    @Test
    fun testOpenTagOneAttribute() {
        layer!!.addOpenTag(Element("tag").withAttribute("key", "value"))
        assertEquals("<tag key=\"value\">", layer.toString())
    }

    @Test
    fun testOpenTagTwoAttributes() {
        val attributes = XmlUtils.newAttributes()
        attributes["key1"] = "value1"
        attributes["key2"] = "value2"
        layer!!.addOpenTag(Element("tag", attributes))
        assertEquals("<tag key1=\"value1\" key2=\"value2\">", layer.toString())
    }

    @Test
    fun testOpenTagWithAmpsersand() {
        layer!!.addOpenTag(Element("ref").withAttribute("target", "http://www.host.nl/nnbw?source=1&page_number=11"))
        assertEquals("<ref target=\"http://www.host.nl/nnbw?source=1&amp;page_number=11\">", layer.toString())
    }

    @Test
    fun testCloseTagByName() {
        layer!!.addCloseTag("tag")
        assertEquals("</tag>", layer.toString())
    }

    @Test
    fun testCloseTagByElement() {
        layer!!.addCloseTag(Element("tag"))
        assertEquals("</tag>", layer.toString())
    }

    @Test
    fun testSequence() {
        layer!!.addOpenTag("h1")
        layer!!.addLiteral("II")
        layer!!.addCloseTag("h1")
        layer!!.addOpenTag(Element("p").withAttribute("id", "p2.0"))
        layer!!.addLiteral("Er wordt gesproken over de \"staatsfabriek\".")
        layer!!.addCloseTag("p")
        assertEquals(
            "<h1>II</h1><p id=\"p2.0\">Er wordt gesproken over de \"staatsfabriek\".</p>",
            layer.toString()
        )
    }
}
