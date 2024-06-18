package nl.knaw.huygens.tei

import com.google.common.collect.Maps
import org.xml.sax.Attributes

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

class XmlUtils private constructor() {
    init {
        throw AssertionError("Non-instantiable class")
    }

    companion object {
        fun newAttributes(): MutableMap<String, String> {
            // use LinkedHashMap to preserve attribute order
            return Maps.newLinkedHashMap()
        }

        fun newAttributes(key: String, value: String): Map<String, String> {
            val map = newAttributes()
            map[key] = value
            return map
        }

        fun convertSAXAttributes(attributes: Attributes, useQualifiedName: Boolean): MutableMap<String, String> {
            val map = newAttributes()
            for (i in 0 until attributes.length) {
                val key = if (useQualifiedName) attributes.getQName(i) else attributes.getLocalName(i)
                val value = attributes.getValue(i)
                map[key] = value
            }
            return map
        }
    }
}
