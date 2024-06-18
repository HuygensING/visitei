package nl.knaw.huygens.tei.export

import nl.knaw.huygens.tei.DelegatingVisitor
import nl.knaw.huygens.tei.XmlContext
import nl.knaw.huygens.tei.handlers.RenderElementHandler
import nl.knaw.huygens.tei.handlers.RenderTextHandler

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
 * Converts a DOM to xml.
 *
 * Notes:
 * - attributes of elements may be reordered.
 * - the internal format of Java strings uses (Unix) line endings '\n',
 * hence the exported format also uses these.
 * - the result may differ in whitespace after the close of the root element.
 * - only three entities are handled.
 * - xml comments are preserved.
 */
class ExportVisitor : DelegatingVisitor<XmlContext>(XmlContext()) {
    init {
        setDefaultElementHandler(RenderElementHandler())
        setTextHandler(object : RenderTextHandler<XmlContext>() {
            override fun filterText(content: String): String {
                val n = content.length
                val builder = StringBuilder((n * 1.1).toInt())
                for (i in 0 until n) {
                    when (val c = content[i]) {
                        '<' -> builder.append("&lt;")
                        '>' -> builder.append("&gt;")
                        '&' -> builder.append("&amp;")
                        else -> builder.append(c)
                    }
                }
                return builder.toString()
            }
        })
    }
}
