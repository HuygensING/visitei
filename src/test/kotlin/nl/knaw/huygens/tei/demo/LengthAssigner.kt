package nl.knaw.huygens.tei.demo

import java.io.File
import nl.knaw.huygens.tei.DelegatingVisitor
import nl.knaw.huygens.tei.Document
import nl.knaw.huygens.tei.Element
import nl.knaw.huygens.tei.ElementHandler
import nl.knaw.huygens.tei.Traversal
import nl.knaw.huygens.tei.XmlContext
import nl.knaw.huygens.tei.export.ExportVisitor
import nl.knaw.huygens.tei.export.Files

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

/*
 * Demonstrates the use of a visitor for assigning an attribute to elements
 * in a TEI document. Note the difference in the output for the paragraph
 * containing a seg element:
 * <p length="1559"><seg type="incipit">Magno me ...
 * <p length="1482"><seg length="77" type="incipit">Magno me ...
 */
object LengthAssigner {
    @JvmStatic
    fun main(args: Array<String>) {
        val file = File("data/test/grotius/a0001.xml")
        val xml: String = Files.readTextFromFile(file)

        println("--------------------")
        process(xml, "p")
        println("--------------------")
        process(xml, "p", "seg")
        println("--------------------")
    }

    private fun process(xml: String, vararg names: String) {
        val document = Document.createFromXml(xml, false)
        val lengthVisitor = LengthVisitor(*names)
        document.accept(lengthVisitor)
        val exportVisitor = ExportVisitor()
        document.accept(exportVisitor)
        println(exportVisitor.context.result)
    }

    /**
     * Visitor that uses ElementHandler for specified elements.
     */
    private class LengthVisitor(vararg elementNames: String) : DelegatingVisitor<XmlContext>(XmlContext()) {
        init {
            addElementHandler(LengthHandler(), *elementNames)
        }
    }

    /**
     * Handler that assignes text length to elements.
     */
    private class LengthHandler : ElementHandler<XmlContext> {
        override fun enterElement(element: Element, context: XmlContext): Traversal {
            context.openLayer()
            return Traversal.NEXT
        }

        override fun leaveElement(element: Element, context: XmlContext): Traversal {
            val text = context.closeLayer()
            val length = text.length.toString()
            element.setAttribute("length", length)
            return Traversal.NEXT
        }
    }
}
