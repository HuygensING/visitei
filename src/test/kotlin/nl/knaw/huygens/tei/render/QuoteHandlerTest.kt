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

class QuoteHandlerTest {
    private fun process(xml: String): String {
        val document = Documents.newDocument(xml)
        val visitor = DelegatingVisitor(XmlContext())
        visitor.addElementHandler(QuoteHandler(), "q")
        document.accept(visitor)
        return visitor.result
    }

    @Test
    fun testNoQuote() {
        assertEquals("text", process("<seg>text</seg>"))
    }

    @Test
    fun testPlainQuote() {
        assertEquals("&#8216;text&#8217;", process("<q>text</q>"))
    }

    @Test
    fun testOtherQuote() {
        assertEquals("&#8216;text&#8217;", process("""<q rend="x">text</q>"""))
    }

    @Test
    fun testBlockQuote() {
        assertEquals("""<div class="blockquote">text</div>""", process("""<q rend="bq">text</q>"""))
    }
}
