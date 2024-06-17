package nl.knaw.huygens.tei.handlers

import nl.knaw.huygens.tei.Comment
import nl.knaw.huygens.tei.CommentHandler
import nl.knaw.huygens.tei.Context
import nl.knaw.huygens.tei.Traversal

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
 * Renders comments as is
 */
class RenderCommentHandler<T : Context> : CommentHandler<T> {
    override fun visitComment(comment: Comment, context: T): Traversal {
        context.addLiteral(comment.toString())
        return Traversal.NEXT
    }
}
