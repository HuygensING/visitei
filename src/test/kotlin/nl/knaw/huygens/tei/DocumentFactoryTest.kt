package nl.knaw.huygens.tei

import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.Test

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

class DocumentFactoryTest {
    @Test
    fun `test xmlns Attributes are handled correctly`() {
        val xml = """<TEI xmlns="http://www.tei-c.org/ns/1.0"
            | xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            | xsi:schemaLocation="http://www.tei-c.org/ns/1.0 http://www.tei-c.org/release/xml/tei/custom/schema/xsd/tei_all.xsd"> | 
            | </TEI>""".trimMargin()
        val doc = Document.createFromXml(xml, true)
        val elements = doc.getElementsByTagName("TEI")
        assertEquals(elements.size.toLong(), 1)
        val tei = elements[0]
        val attributeNames = tei.attributeNames
        assertTrue(attributeNames.contains("xmlns:xsi"))
        assertTrue(attributeNames.contains("xsi:schemaLocation"))
        assertTrue(attributeNames.contains("xmlns"))
    }
}

