package nl.knaw.huygens.tei

import kotlin.test.*
import org.junit.Test

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

class TeiUtilsTest {
    private val testDocument: Document
        get() {
            val xml = "<root><a n=\"1\"><a n=\"2\"><x/></a></a><y/></root>"
            return Document.createFromXml(xml, false)
        }

    @Test
    fun testGetAncestorWithMissingParent() {
        val document = testDocument
        val elements = document.getElementsByTagName("y")
        assertEquals(1, elements.size.toLong())
        val element = elements[0]
        val ancestor: Element? = TeiUtils.getAncestor(element, "a")
        assertNull(ancestor)
    }

    @Test
    fun testGetAncestorWithParent() {
        val document = testDocument
        val elements = document.getElementsByTagName("x")
        assertEquals(1, elements.size.toLong())
        val element = elements[0]
        val ancestor: Element? = TeiUtils.getAncestor(element, "a")
        assertNotNull(ancestor)
        assertTrue(ancestor.hasName("a") && ancestor.hasAttribute("n", "2"))
    }
}
