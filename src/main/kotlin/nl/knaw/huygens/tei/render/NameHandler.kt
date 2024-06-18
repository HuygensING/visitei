package nl.knaw.huygens.tei.render

import nl.knaw.huygens.tei.Element
import nl.knaw.huygens.tei.ElementHandler
import nl.knaw.huygens.tei.Traversal
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

/**
 * Renders the TEI `seg` element.
 */
class NameHandler : ElementHandler<XmlContext> {
    private fun isPerson(element: Element): Boolean {
        return element.hasType("person")
    }

    override fun enterElement(element: Element, context: XmlContext): Traversal {
        if (isPerson(element)) {
            context.addOpenTag(NAME_SPAN)
        }
        return Traversal.NEXT
    }

    override fun leaveElement(element: Element, context: XmlContext): Traversal {
        if (isPerson(element)) {
            context.addCloseTag(NAME_SPAN)
        }
        return Traversal.NEXT
    }

    companion object {
        private val NAME_SPAN: Element = Element.newSpanElement("name")
    }
}
