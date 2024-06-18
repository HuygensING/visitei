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

class LineGroupHandlerTest {
    private fun process(xml: String): String {
        val document = Documents.newDocument(xml)
        val visitor = DelegatingVisitor(XmlContext())
        visitor.addElementHandler(LineGroupHandler(), "lg", "l")
        document.accept(visitor)
        return visitor.result
    }

    @Test
    fun testNoLineGroup() {
        assertEquals("text", process("<seg>text</seg>"))
    }

    @Test
    fun testLineGroupNoPoem() {
        assertEquals("12", process("<lg><l>1</l><l>2</l></lg>"))
    }

    @Test
    fun testLineGroupPoem() {
        assertEquals(
            """<div class="poem">1<br/>2<br/></div>""",
            process("""<lg type="poem"><l>1</l><l>2</l></lg>""")
        )
    }
}
