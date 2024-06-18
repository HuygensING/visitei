package nl.knaw.huygens.tei

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

class TeiUtils private constructor() {
    init {
        throw AssertionError("Non-instantiable class")
    }

    companion object {
        /**
         * Returns the nearest ancestor with the specified name,
         * `null` if no such an element exists.
         */
        fun getAncestor(origElement: Element, name: String): Element? {
            var element: Element? = origElement
            element = element?.parent
            while (element != null) {
                if (element.hasName(name)) {
                    return element
                }
                element = element.parent
            }
            return null
        }

        /**
         * Returns `true` if the specified element has an ancestor
         * with the specified name, `false` otherwise.
         */
        fun hasAncestor(element: Element, name: String): Boolean =
            getAncestor(element, name) != null

        /**
         * Returns `true` if the specified element has an ancestor
         * with one of the specified names, `false` otherwise.
         */
        fun hasAncestor(originalElement: Element, names: Collection<String>): Boolean {
            var element: Element? = originalElement
            element = element?.parent
            while (element != null) {
                if (names.contains(element.name)) {
                    return true
                }
                element = element.parent
            }
            return false
        }
    }
}
