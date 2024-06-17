package nl.knaw.huygens.tei.handlers

import nl.knaw.huygens.tei.Context

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

class XmlTextHandler<T : Context> : RenderTextHandler<T>() {
    override fun filterText(text: String): String {
        val n = text.length
        val builder = StringBuilder((n * 1.1).toInt())
        for (i in 0 until n) {
            when (val c = text[i]) {
                '<' -> builder.append("&lt;")
                '>' -> builder.append("&gt;")
                '&' -> builder.append("&amp;")
                else -> builder.append(c)
            }
        }
        return builder.toString()
        //    return StringEscapeUtils.escapeXml(text)
    }
}
