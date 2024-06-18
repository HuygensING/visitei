package nl.knaw.huygens.tei

import com.google.common.collect.Maps

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
 * Visitor for analyzing the elements used in a document.
 */
class ElementAnalyzerVisitor : DefaultVisitor() {
    private class Count(var name: String) {
        var count: Int = 0
    }

    private val counts: MutableMap<String, Count> =
        Maps.newTreeMap()

    private fun register(name: String) {
        var item = counts[name]
        if (item == null) {
            item = Count(name)
            counts[name] = item
        }
        item.count++
    }

    fun displayCounts() {
        for (count in counts.values) {
            System.out.printf("%-20s %10d%n", count.name, count.count)
        }
    }

    override fun enterElement(element: Element): Traversal {
        if (element.hasType()) {
            register(element.name + "-t:" + element.type)
        } else if (element.hasRendition()) {
            register(element.name + "-r:" + element.rendition)
        } else {
            register(element.name)
        }
        return Traversal.NEXT
    }
}
