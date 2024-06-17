package nl.knaw.huygens.tei.render

import nl.knaw.huygens.tei.DelegatingVisitor
import nl.knaw.huygens.tei.Element
import nl.knaw.huygens.tei.XmlContext
import nl.knaw.huygens.tei.handlers.SkipElementHandler
import nl.knaw.huygens.tei.handlers.TransformElementHandler

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

/**
 * Basisc render vistor for TEI documents.
 */
class RenderVisitor(imageUrlResolver: UrlResolver) : DelegatingVisitor<XmlContext>(XmlContext()) {
    init {
        // Graphics: <figure>, <figDesc>, <graphic>
        addElementHandler(FigureHandler(), "figure")
        addElementHandler(SkipElementHandler(), "figDesc")
        addElementHandler(GraphicHandler(imageUrlResolver), "graphic")

        // Lists: <list>, <item>, <label>
        addElementHandler(ListHandler(), "list", "item", "label")

        // Tables: <table>, <row>, <cell>
        addElementHandler(TransformElementHandler("table"), "table")
        addElementHandler(TransformElementHandler("tr"), "row")
        addElementHandler(TableCellHandler(), "cell")

        // Remaining elements
        addElementHandler(TransformElementHandler(Element.newDivElement("opener")), "opener")
        addElementHandler(TransformElementHandler(Element.newDivElement("closer")), "closer")
        addElementHandler(DivisionHandler(), "div")
        addElementHandler(HeadHandler(), "head")
        addElementHandler(HighlightHandler(), "hi")
        addElementHandler(TransformElementHandler("br"), "lb")
        addElementHandler(LineGroupHandler(), "lg", "l")
        addElementHandler(NameHandler(), "name")
        addElementHandler(ParagraphHandler(), "p")
        addElementHandler(QuoteHandler(), "q")
        addElementHandler(SegmentHandler(), "seg")
    }

    override val result: String
        get() = context.result
}
