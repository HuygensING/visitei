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

class DivisionHandlerTest {
    private fun process(xml: String): String {
        val document = Documents.newDocument(xml)
        val visitor = DelegatingVisitor(XmlContext())
        visitor.addElementHandler(DivisionHandler(), "div")
        document.accept(visitor)
        return visitor.result
    }

    @Test
    fun testNoDiv() {
        assertEquals("text", process("<span>text</span>"))
    }

    @Test
    fun testDivWithoutType() {
        assertEquals("<div>text</div>", process("<div>text</div>"))
    }

    @Test
    fun testDivWithType() {
        assertEquals("""<div class="x">text</div>""", process("""<div type="x">text</div>"""))
    }
}
