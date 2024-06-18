package nl.knaw.huygens.tei

/*
 * #%L
 * VisiTEI
 * =======
 * Copyright (C) 2011 - 2024 Huygens ING
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

class TextLayer {
    private val builder = StringBuilder()

    fun length(): Int = builder.length

    override fun toString(): String = builder.toString()

    // -------------------------------------------------------------------
    fun addLiteral(literal: Any?) {
        if (literal != null) {
            builder.append(literal.toString())
        }
    }

    fun addOpenTag(element: Element) {
        element.appendOpenTagTo(builder)
    }

    fun addOpenTag(elementName: String) {
        builder.append('<').append(elementName).append('>')
    }

    fun addCloseTag(element: Element) {
        element.appendCloseTagTo(builder)
    }

    fun addCloseTag(elementName: String) {
        builder.append('<').append('/').append(elementName).append('>')
    }

    fun addEmptyElementTag(element: Element) {
        element.appendEmptyElementTagTo(builder)
    }

    fun addEmptyElementTag(elementName: String) {
        builder.append('<').append(elementName).append('/').append('>')
    }

    fun addComment(comment: String) {
        builder.append("<!-- ").append(comment).append(" -->")
    }

    fun addElement(element: Element, content: Any) {
        addOpenTag(element)
        addLiteral(content)
        addCloseTag(element)
    }
}
