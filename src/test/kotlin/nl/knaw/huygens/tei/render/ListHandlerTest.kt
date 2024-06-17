package nl.knaw.huygens.tei.render

import kotlin.test.assertEquals
import org.junit.Test
import nl.knaw.huygens.tei.DelegatingVisitor
import nl.knaw.huygens.tei.Documents
import nl.knaw.huygens.tei.XmlContext

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

class ListHandlerTest {
    private fun process(xml: String): String {
        val document = Documents.newDocument(xml)
        val visitor = DelegatingVisitor(XmlContext())
        visitor.addElementHandler(ListHandler(), "list", "item", "label")
        document.accept(visitor)
        return visitor.result
    }

    @Test
    fun testNoList() {
        assertEquals("text", process("<seg>text</seg>"))
    }

    @Test
    fun testListWithoutLabels() {
        assertEquals("<ul><li>a</li><li>b</li></ul>", process("<list><item>a</item><item>b</item></list>"))
    }

    @Test
    fun testListWithLabels() {
        val xml = "<list><label>1.</label><item>a</item><label>2.</label><item>b</item></list>"
        val rendered =
            """<ul><li><span class="listLabel">1.</span>a</li><li><span class="listLabel">2.</span>b</li></ul>"""
        assertEquals(rendered, process(xml))
    }
}
