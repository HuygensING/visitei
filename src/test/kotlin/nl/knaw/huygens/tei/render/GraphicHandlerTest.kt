package nl.knaw.huygens.tei.render

import kotlin.test.assertEquals
import kotlin.test.assertTrue
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

class GraphicHandlerTest {
    private class TestResolver : UrlResolver {
        override fun resolve(key: String): String = "base/$key"
    }

    private fun process(xml: String): String {
        val document = Documents.newDocument(xml)
        val visitor = DelegatingVisitor(XmlContext())
        visitor.addElementHandler(GraphicHandler(TestResolver()), "graphic")
        document.accept(visitor)
        return visitor.result
    }

    @Test
    fun testNoGraphic() {
        assertEquals("text", process("<seg>text</seg>"))
    }

    @Test
    fun testGraphicWithoutUrl() {
        assertEquals("", process("""<graphic id="test.gif"/>"""))
    }

    @Test
    fun testGraphic() {
        // N.B. Order of attributes depends on implementation
        val result = process("""<graphic url="test.gif"/>""")
        assertTrue(result.startsWith("<img "))
        assertTrue(result.contains(""" alt="[test.gif]""""))
        assertTrue(result.contains(""" src="base/test.gif""""))
        assertTrue(result.endsWith("/>"))
    }

    @Test
    fun testGraphicWithContent() {
        // N.B. Order of attributes depends on implementation
        val result = process("""<graphic url="test.gif">text</graphic>""")
        assertTrue(result.startsWith("<img "))
        assertTrue(result.contains(""" alt="[test.gif]""""))
        assertTrue(result.contains(""" src="base/test.gif""""))
        assertTrue(result.endsWith("/>"))
    }
}
