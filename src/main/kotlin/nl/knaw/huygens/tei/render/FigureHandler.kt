package nl.knaw.huygens.tei.render

import nl.knaw.huygens.tei.Element
import nl.knaw.huygens.tei.ElementHandler
import nl.knaw.huygens.tei.Traversal
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

/**
 * Renders the TEI `figure` element.
 */
class FigureHandler : ElementHandler<XmlContext> {
    private fun renderedElement(element: Element): Element {
        return if (element.hasRendition("inline")) FIGURE_SPAN else FIGURE_DIV
    }

    override fun enterElement(element: Element, context: XmlContext): Traversal {
        context.addOpenTag(renderedElement(element))
        return Traversal.NEXT
    }

    override fun leaveElement(element: Element, context: XmlContext): Traversal {
        context.addCloseTag(renderedElement(element))
        return Traversal.NEXT
    }

    companion object {
        private val FIGURE_DIV: Element = Element.newDivElement("figure")
        private val FIGURE_SPAN: Element = Element.newSpanElement("figure")
    }
}
