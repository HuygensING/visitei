package nl.knaw.huygens.tei

import com.google.common.collect.Lists
import org.xml.sax.InputSource

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

class Document : Node() {
    var root: Element? = null
    val headNodes: MutableList<Node> = ArrayList()
    val footNodes: MutableList<Node> = ArrayList()

    /**
     * Returns a list of all elements with the specified tag name, in the order
     * in which they are encountered in preorder traversal of the document tree.
     * @param name the specified tag name
     * @return a list of all elements with the specified tag name
     */
    fun getElementsByTagName(name: String): List<Element> {
        val elements: MutableList<Element> = Lists.newArrayList()
        val visitor: Visitor = object : DefaultVisitor() {
            override fun enterElement(element: Element): Traversal {
                if (element.hasName(name)) {
                    elements.add(element)
                }
                return Traversal.NEXT
            }
        }
        accept(visitor)
        return elements
    }

    /**
     * Returns a list of all elements with the specified tag name and attribute
     * value, in the order in which they are encountered in preorder traversal
     * of the document tree.
     * @param name the tag name to match
     * @param key the key of the attribute to match
     * @param value the value of the attribute to match
     * @return a list of all elements that meet the criteria
     */
    fun getElementsByNameAndAttribute(name: String, key: String, value: String): List<Element> {
        val elements: MutableList<Element> = Lists.newArrayList()
        val visitor: Visitor = object : DefaultVisitor() {
            override fun enterElement(element: Element): Traversal {
                if (element.hasName(name) && element.hasAttribute(key, value)) {
                    elements.add(element)
                }
                return Traversal.NEXT
            }
        }
        accept(visitor)
        return elements
    }

    // --- Visiting ------------------------------------------------------
    override fun accept(visitor: Visitor): Traversal {
        if (visitor.enterDocument(this) == Traversal.NEXT) {
            for (node in headNodes) {
                node.accept(visitor)
            }
            if (root != null) {
                root!!.accept(visitor)
            }
            for (node in footNodes) {
                node.accept(visitor)
            }
        }
        return visitor.leaveDocument(this)
    }

    companion object {
        /**
         *
         * @param inputSource  inputSource
         * @param preserveNamespacePrefix preserve the namespace prefix for an Elements attributes
         * @return the Document
         */
        fun createFromInputSource(inputSource: InputSource, preserveNamespacePrefix: Boolean): Document {
            return DocumentFactory(inputSource, preserveNamespacePrefix).document
        }

        /**
         *
         * @param xml  xml source
         * @param preserveNamespacePrefix preserve the namespace prefix for an Elements attributes
         * @return the Document
         */
        fun createFromXml(xml: String, preserveNamespacePrefix: Boolean): Document {
            return DocumentFactory(xml, preserveNamespacePrefix).document
        }

        /**
         * @param xml  xml source
         * @return the Document
         */
        @Deprecated(
            message = """use {@link #createFromXml(String,boolean)} to explicitly indicate whether to preserve the namespace prefix for an Elements attributes""",
            replaceWith = ReplaceWith("DocumentFactory(xml, false).document", "nl.knaw.huygens.tei.DocumentFactory")
        )
        fun createFromXml(xml: String): Document {
            return DocumentFactory(xml, false).document
        }
    }
}
