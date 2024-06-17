package nl.knaw.huygens.tei.export

import java.io.File
import kotlin.test.assertEquals
import org.junit.Test
import nl.knaw.huygens.tei.Document

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

class ExportVisitorTest {
    @Test
    fun testBasicXml() {
        val xml = "<TEI><text><head><h1>Hello World</h1></head></text></TEI>"
        assertEquals(xml, process(xml))
    }

    @Test
    fun testEntities() {
        val xml = "<TEI><text>ge&lt;daen&gt;, &amp;c.</text></TEI>"
        assertEquals(xml, process(xml))
    }

    @Test
    fun testProcessingInstruction() {
        val xml = """<?oxygen RNGSchema="AF.rng" type="xml"?><TEI><text>text</text></TEI>"""
        assertEquals(xml, process(xml))
    }

    //  @Test
    fun testCDATA() {
        val xml = "<TEI><text><![CDATA[no <escaping> necessary & needed in CDATA]]></text></TEI>"
        assertEquals(xml, process(xml))
    }

    @Test
    fun testComments() {
        val xml = "<TEI><!-- comments are preserved --></TEI>"
        assertEquals(xml, process(xml))
    }

    @Test
    fun testCommentsOutsideElementTreeGetCopiedToo() {
        val xmlIn = """<!-- first -->
<!-- before -->
<TEI><!-- inside --></TEI>
<!-- after -->
<!-- last -->"""
        val xmlOut = xmlIn.replace("\n", "")
        assertEquals(xmlOut, process(xmlIn))
    }

    @Test
    fun testDontSplitUpText() {
        val xml = """<text><div type="opener" resp="WR"><p>Illustrissime Domine lega«te,</p></div></text>"""
        assertEquals(xml, process(xml))
    }

    @Test
    fun testComplexXml() {
        // File has UNIX style EOL's.
        val file = File("data/test/grotius/a0001.xml")
        val xml: String = Files.Companion.readTextFromFile(file).trim { it <= ' ' }
        // Normalize order of attributes
        val normalized = process(xml)
        assertEquals(xml.length.toLong(), normalized.length.toLong())
        // Normalized xml should be idempotent
        assertEquals(normalized, process(normalized))
    }

    private fun process(xml: String): String {
        val document = Document.createFromXml(xml, true)
        val visitor = ExportVisitor()
        document.accept(visitor)
        return visitor.context.result
    }
}
