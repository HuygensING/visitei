package nl.knaw.huygens.tei

import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
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

class ElementTest {
    private var builder: StringBuilder? = null

    @Before
    fun createBuilder() {
        builder = StringBuilder()
    }

    private fun assertRendered(expected: String) {
        assertEquals(expected, builder.toString())
    }

    @Test
    fun noAttributes() {
        val element = Element("tagname")
        element.appendOpenTagTo(builder)
        element.appendCloseTagTo(builder)
        assertRendered("<tagname></tagname>")
    }

    @Test
    fun oneAttribute() {
        val element = Element("name").withAttribute("key", "value")
        element.appendOpenTagTo(builder)
        assertRendered("<name key=\"value\">")
    }

    @Test
    fun twoAttributesInOrder() {
        val element = Element("name").withAttribute("key1", "value1").withAttribute("key2", "value2")
        element.appendOpenTagTo(builder)
        assertRendered("<name key1=\"value1\" key2=\"value2\">")
    }

    @Test
    fun twoAttributesOutOfOrder() {
        val element = Element("name").withAttribute("key2", "value2").withAttribute("key1", "value1")
        element.appendOpenTagTo(builder)
        assertRendered("<name key2=\"value2\" key1=\"value1\">")
    }

    @Test
    fun overwriteAttribute() {
        val element = Element("name").withAttribute("key1", "value1").withAttribute("key1", "value2")
        element.appendOpenTagTo(builder)
        assertRendered("<name key1=\"value2\">")
    }

    @Test
    fun emptyElementTag() {
        val element = Element("name").withAttribute("key", "value")
        element.appendEmptyElementTagTo(builder)
        assertRendered("<name key=\"value\"/>")
    }

    @Test
    fun testHasAttribute() {
        assertFalse(Element("name").hasAttribute("key"))
        assertFalse(Element("name").withAttribute("key", "").hasAttribute("key"))
        assertFalse(Element("name").withAttribute("other", "value").hasAttribute("key"))
        assertTrue(Element("name").withAttribute("key", "value").hasAttribute("key"))
    }

    @Test
    fun testHasLanguage() {
        assertFalse(Element("name").hasLanguage())
        assertFalse(Element("name").withAttribute("lang", "").hasLanguage())
        assertFalse(Element("name").withAttribute("key", "value").hasLanguage())
        assertTrue(Element("name").withAttribute("lang", "value").hasLanguage())
    }

    @Test
    fun testSetLanguage() {
        val element = Element("name")
        assertEquals("", element.language)
        element.language = "fr"
        assertEquals("fr", element.language)
        element.language = "la"
        assertEquals("la", element.language)
    }

    @Test
    fun testHasRendition() {
        assertFalse(Element("name").hasRendition())
        assertFalse(Element("name").withAttribute(Element.RENDITION, "").hasRendition())
        assertFalse(Element("name").withAttribute("other", "value").hasRendition())
        assertTrue(Element("name").withAttribute(Element.RENDITION, "value").hasRendition())
    }

    @Test
    fun testHasType() {
        assertFalse(Element("name").withAttribute("key", "value").hasType("value"))
        assertFalse(Element("name").withAttribute(Element.TYPE, "value").hasType("type"))
        assertTrue(Element("name").withAttribute(Element.TYPE, "value").hasType("value"))
    }

    @Test
    fun testGeneratedElementHasNoLocation() {
        val element = Element("generated")
        assertEquals(-1, element.startLine.toLong())
        assertEquals(-1, element.startColumn.toLong())
    }

    @Test
    fun testMissingIntAttribute() {
        val element = Element("name").withAttribute("key", "37")
        assertEquals(42, element.getIntAttribute("missing", 42).toLong())
    }

    @Test
    fun testInvalidIntAttribute() {
        val element = Element("name").withAttribute("key", "text")
        assertEquals(42, element.getIntAttribute("key", 42).toLong())
    }

    @Test
    fun testValidIntAttribute() {
        val element = Element("name").withAttribute("key", "37")
        assertEquals(37, element.getIntAttribute("key", 42).toLong())
    }

    @Test
    fun testMissingDoubleAttribute() {
        val element = Element("name").withAttribute("key", "33.3")
        assertEquals(42.0, element.getDoubleAttribute("missing", 42.0), 0.001)
    }

    @Test
    fun testInvalidDoubleAttribute() {
        val element = Element("name").withAttribute("key", "text")
        assertEquals(42.0, element.getDoubleAttribute("key", 42.0), 0.001)
    }

    @Test
    fun testValidDoubleAttribute() {
        val element = Element("name").withAttribute("key", "33.3")
        assertEquals(33.3, element.getDoubleAttribute("key", 42.0), 0.001)
    }

    @Test
    fun testGetAttributeNames() {
        val element = Element("name").withAttribute("key1", "value1").withAttribute("key2", "value2")
        val attributeNames = element.attributeNames
        assertEquals(2, attributeNames.size.toLong())
        assertTrue(attributeNames.contains("key1"))
        assertTrue(attributeNames.contains("key2"))
    }

    @Test
    fun testHasParentWithName() {
        val element = Element("name")
        assertFalse(element.hasParentWithName("parent"))
        var parent = Element("name")
        element.parent = parent
        assertFalse(element.hasParentWithName("parent"))
        parent = Element("parent")
        element.parent = parent
        assertTrue(element.hasParentWithName("parent"))
    }

    @Test
    fun testCopyOfGeneratesACopy() {
        val parent = Element("parent")
        val original = Element("name").withAttribute("key1", "value1").withAttribute("key2", "value2")
        original.parent = parent
        val copy = Element.copyOf(original)
        assertEquals(copy.name, original.name)
        assertEquals(copy.attributes, original.attributes)
        assertNull(copy.parent)
        copy.setAttribute("key3", "value3")
        assertEquals(3, copy.attributeNames.size.toLong())
        assertEquals(2, original.attributeNames.size.toLong())
    }

    @Test
    fun testCopyMissingAttribute() {
        val source = Element("source")
        val target = Element("target").copyAttributeFrom(source, "key1", "key2")
        assertEquals("", target.getAttribute("key1"))
        assertEquals("", target.getAttribute("key2"))
    }

    @Test
    fun testCopyExistingAttribute() {
        val source = Element("source").withAttribute("key1", "value")
        val target = Element("target").copyAttributeFrom(source, "key1", "key2")
        assertEquals("", target.getAttribute("key1"))
        assertEquals("value", target.getAttribute("key2"))
    }
}
