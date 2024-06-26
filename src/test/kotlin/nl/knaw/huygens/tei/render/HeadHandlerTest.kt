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

class HeadHandlerTest {
    private fun process(xml: String): String {
        val document = Documents.newDocument(xml)
        val visitor = DelegatingVisitor(XmlContext())
        visitor.addElementHandler(HeadHandler(), "head")
        document.accept(visitor)
        return visitor.result
    }

    @Test
    fun testHeadWithoutRend() {
        assertEquals("<h3>text</h3>", process("""<div type="section"><head>text</head></div>"""))
    }

    @Test
    fun testHeadWithRend() {
        assertEquals("<h4>text</h4>", process("""<div type="section"><head rend="h4">text</head></div>"""))
    }

    @Test
    fun testHeadWithLang() {
        assertEquals("""<h3 lang="la">text</h3>""", process("""<div><head lang="la">text</head></div>"""))
    }
}
