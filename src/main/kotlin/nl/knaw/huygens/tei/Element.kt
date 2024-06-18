package nl.knaw.huygens.tei

import java.text.MessageFormat
import com.google.common.base.Joiner
import com.google.common.collect.Lists

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

class Element @JvmOverloads constructor(// -------------------------------------------------------------------
    var name: String,
    val attributes: MutableMap<String, String> = XmlUtils.newAttributes()
) : SubNode() {
    private val nodes: MutableList<Node> = Lists.newArrayList()

    fun withAttribute(key: String, value: String): Element {
        setAttribute(key, value)
        return this
    }

    /**
     * @param name  element name
     * @param key   attribute key
     * @param value attribute value
     */
    @Deprecated(
        """As of 0.4.7, use new {@link #Element(String)}.{@link #withAttribute(String, String)} instead
    """
    )
    constructor(name: String, key: String, value: String) : this(name, XmlUtils.newAttributes()) {
        attributes[key] = value
    }

    // --- Visiting ------------------------------------------------------
    override fun accept(visitor: Visitor): Traversal {
        if (visitor.enterElement(this) == Traversal.NEXT) {
            for (node in nodes) {
                if (node.accept(visitor) == Traversal.STOP) {
                    break
                }
            }
        }
        return visitor.leaveElement(this)
    }

    // --- Structure -----------------------------------------------------
    fun addNode(node: Node) {
        nodes.add(node)
    }

    fun hasChildren(): Boolean = (nodes.size != 0)

    fun hasNoChildren(): Boolean = (nodes.size == 0)

    fun getNodes(): List<Node> = nodes

    fun hasName(name: String): Boolean =
        this.name == name

    override var startLine: Int
        /**
         * @return the line number in the source xml where the start tag of this element ends, if available;
         * -1 otherwise
         */
        get() = super.startLine
        set(startLine) {
            super.startLine = startLine
        }

    override var startColumn: Int
        /**
         * @return the column number in the source xml where the start tag of this element ends, if available;
         * -1 otherwise
         */
        get() = super.startColumn
        set(startColumn) {
            super.startColumn = startColumn
        }

    fun getAttribute(key: String): String {
        val value = attributes[key]
        return if ((value != null)) value else ""
    }

    fun getAttribute(key: String, defaultValue: String): String {
        val value = attributes[key]
        return if ((value != null)) value else defaultValue
    }

    /**
     * Returns an integer attribute value, or the specified default value
     * if the attribute is missing or if conversion to integer fails.
     *
     * @param key the attribute key
     * @param defaultValue the specified default
     * @return an integer attribute value, or the specified default value
     */
    fun getIntAttribute(key: String, defaultValue: Int): Int {
        val value = attributes[key]
        if (value != null) {
            try {
                return value.toInt()
            } catch (e: NumberFormatException) {
                // ignore
            }
        }
        return defaultValue
    }

    /**
     * Returns a double attribute value, or the specified default value
     * if the attribute is missing or if conversion to double fails.
     *
     * @param key the attribute key
     * @param defaultValue the specified default
     * @return a double attribute value, or the specified default value
     */
    fun getDoubleAttribute(key: String, defaultValue: Double): Double {
        val value = attributes[key]
        if (value != null) {
            try {
                return value.toDouble()
            } catch (e: NumberFormatException) {
                // ignore
            }
        }
        return defaultValue
    }

    fun hasAttribute(key: String): Boolean {
        val value = attributes[key]
        return !value.isNullOrEmpty()
    }

    fun hasAttribute(key: String, value: String): Boolean {
        // TODO decide whether to check for null value
        return value == attributes[key]
    }

    fun removeAttribute(key: String) {
        attributes.remove(key)
    }

    fun setAttribute(key: String, value: String) {
        attributes[key] = value
    }

    fun copyAttributeFrom(source: Element, sourceKey: String, targetKey: String): Element {
        val value = source.getAttribute(sourceKey)
        if (value.isNotEmpty()) {
            setAttribute(targetKey, value)
        }
        return this
    }

    fun copyAttributeFrom(source: Element, key: String): Element {
        return copyAttributeFrom(source, key, key)
    }

    var language: String
        get() = getAttribute(LANGUAGE)
        set(value) {
            setAttribute(LANGUAGE, value)
        }

    fun hasLanguage(): Boolean {
        return hasAttribute(LANGUAGE)
    }

    fun hasLanguage(value: String): Boolean {
        return hasAttribute(LANGUAGE, value)
    }

    val rendition: String
        get() = getAttribute(RENDITION)

    fun hasRendition(): Boolean {
        return hasAttribute(RENDITION)
    }

    fun hasRendition(value: String): Boolean {
        return hasAttribute(RENDITION, value)
    }

    val type: String
        get() = getAttribute(TYPE)

    fun hasType(): Boolean {
        return hasAttribute(TYPE)
    }

    fun hasType(value: String): Boolean {
        return hasAttribute(TYPE, value)
    }

    // --- Output --------------------------------------------------------
    fun appendOpenTagTo(builder: StringBuilder) {
        appendTagWithAttributesTo(builder, false)
    }

    fun appendCloseTagTo(builder: StringBuilder) {
        builder.append('<').append('/').append(name).append('>')
    }

    fun appendEmptyElementTagTo(builder: StringBuilder) {
        appendTagWithAttributesTo(builder, true)
    }

    private fun appendTagWithAttributesTo(builder: StringBuilder, emptyTag: Boolean) {
        builder.append('<').append(name)
        for ((key, value) in attributes) {
            builder.append(' ').append(key).append('=')
            builder.append('"')
            appendAttributeValue(builder, value)
            builder.append('"')
        }
        builder.append(if (emptyTag) "/>" else ">")
    }

    private fun appendAttributeValue(builder: StringBuilder, value: String) {
        val n = value.length
        for (i in 0 until n) {
            when (val c = value[i]) {
                '<' -> builder.append("&lt;")
                '>' -> builder.append("&gt;")
                '&' -> builder.append("&amp;")
                else -> builder.append(c)
            }
        }
    }

    val attributeNames: Set<String>
        get() = attributes.keys

    override fun toString(): String {
        val attributes1: MutableList<String> = Lists.newArrayList()
        for (attributeName in attributes.keys) {
            attributes1.add(MessageFormat.format(" {0}=\"{1}\"", attributeName, getAttribute(attributeName)))
        }
        attributes1.sort()
        return MessageFormat.format("<{0}{1}>", name, Joiner.on("").join(attributes1))
    }

    companion object {
        const val DIV_TAG: String = "div"
        const val SPAN_TAG: String = "span"

        fun newDivElement(className: String): Element {
            return Element(DIV_TAG).withAttribute("class", className)
        }

        fun newSpanElement(className: String): Element {
            return Element(SPAN_TAG).withAttribute("class", className)
        }

        /**
         * Create a new element with the same name and attributes as the given element, but none of its child nodes or end/start lines/columns
         * @param element the element to copy
         * @return the copied element, with the same name and attributes as the given element.
         */
        fun copyOf(element: Element): Element {
            val copy = Element(element.name)
            for ((key, value) in element.attributes) {
                copy.setAttribute(key, value)
            }
            return copy
        }

        // --- Attributes ----------------------------------------------------
        const val LANGUAGE: String = "lang"
        const val RENDITION: String = "rend"
        const val TYPE: String = "type"
    }
}
