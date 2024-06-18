package nl.knaw.huygens.tei

import com.google.common.base.Preconditions
import com.google.common.collect.Maps
import nl.knaw.huygens.tei.handlers.DefaultElementHandler
import nl.knaw.huygens.tei.handlers.RenderCommentHandler
import nl.knaw.huygens.tei.handlers.RenderProcessingInstructionHandler
import nl.knaw.huygens.tei.handlers.RenderTextHandler

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

open class DelegatingVisitor<T : Context>(context: T) : DefaultVisitor() {
    val context: T = Preconditions.checkNotNull<T>(context)
    private val handlers: MutableMap<String, ElementHandler<T>> = Maps.newHashMap()
    private var defaultHandler: ElementHandler<T>
    private var textHandler: TextHandler<T>
    private var commentHandler: CommentHandler<T>
    private var processingInstructionHandler: ProcessingInstructionHandler<T>

    init {
        defaultHandler = DefaultElementHandler()
        textHandler = RenderTextHandler()
        commentHandler = RenderCommentHandler()
        processingInstructionHandler = RenderProcessingInstructionHandler()
    }

    open val result: String
        get() = context.result

    fun setTextHandler(handler: TextHandler<T>) {
        textHandler = Preconditions.checkNotNull(handler)
    }

    fun setCommentHandler(handler: CommentHandler<T>) {
        commentHandler = Preconditions.checkNotNull(handler)
    }

    fun setProcessingInstructionHandler(handler: ProcessingInstructionHandler<T>) {
        processingInstructionHandler = Preconditions.checkNotNull(handler)
    }

    fun setDefaultElementHandler(handler: ElementHandler<T>) {
        defaultHandler = Preconditions.checkNotNull(handler)
    }

    fun addElementHandler(handler: ElementHandler<T>, vararg names: String) {
        Preconditions.checkNotNull(handler)
        for (name in names) {
            handlers[name] = handler
        }
    }

    fun addElementHandler(handler: ElementHandler<T>, names: List<String>) {
        Preconditions.checkNotNull(handler)
        for (name in names) {
            handlers[name] = handler
        }
    }

    fun withElementHandler(handler: ElementHandler<T>, vararg names: String): DelegatingVisitor<T> {
        addElementHandler(handler, *names)
        return this
    }

    fun withElementHandler(handler: ElementHandler<T>, names: List<String>): DelegatingVisitor<T> {
        addElementHandler(handler, names)
        return this
    }

    private fun getElementHandler(element: Element): ElementHandler<T> {
        val handler = handlers[element.name]
        return if ((handler != null)) handler else defaultHandler
    }

    // --- Visiting ------------------------------------------------------
    override fun enterElement(element: Element): Traversal {
        return getElementHandler(element).enterElement(element, context)
    }

    override fun leaveElement(element: Element): Traversal {
        return getElementHandler(element).leaveElement(element, context)
    }

    override fun visitText(text: Text): Traversal {
        return textHandler.visitText(text, context)
    }

    override fun visitComment(comment: Comment): Traversal {
        return commentHandler.visitComment(comment, context)
    }

    override fun visitProcessingInstruction(processingInstruction: ProcessingInstruction): Traversal {
        return processingInstructionHandler.visitProcessingInstruction(processingInstruction, context)
    }
}
