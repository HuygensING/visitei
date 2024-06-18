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

/**
 * Hierarchical visitor.
 */
interface Visitor {
    fun enterDocument(document: Document): Traversal

    fun leaveDocument(document: Document): Traversal

    fun enterElement(element: Element): Traversal

    fun leaveElement(element: Element): Traversal

    fun visitText(text: Text): Traversal

    fun visitComment(comment: Comment): Traversal

    fun visitProcessingInstruction(processingInstruction: ProcessingInstruction): Traversal
}
