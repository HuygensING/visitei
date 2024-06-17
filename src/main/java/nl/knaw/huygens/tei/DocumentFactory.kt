package nl.knaw.huygens.tei

import java.io.IOException
import java.io.StringReader
import java.util.ArrayDeque
import java.util.Deque
import com.google.common.collect.Maps
import org.apache.xerces.parsers.SAXParser
import org.xml.sax.Attributes
import org.xml.sax.InputSource
import org.xml.sax.Locator
import org.xml.sax.SAXException
import org.xml.sax.ext.DefaultHandler2

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

class DocumentFactory(
    inputSource: InputSource,
    private val preserveNameSpacePrefix: Boolean
) : DefaultHandler2() {
    private enum class Section {
        HeadNodes, Elements, FootNodes
    }

    private val elementStack: Deque<Element> = ArrayDeque()
    val document: Document = Document()
    private var locator: Locator? = null
    private var currentSection = Section.HeadNodes

    private val prefixMap: MutableMap<String, String> = Maps.newHashMap()

    constructor(xml: String, preserveNamespacePrefix: Boolean) : this(
        InputSource(StringReader(xml)),
        preserveNamespacePrefix
    )

    init {
        val parser = SAXParser()
        try {
            parser.contentHandler = this
            parser.entityResolver = this
            parser.setProperty("http://xml.org/sax/properties/lexical-handler", this)
            parser.parse(inputSource)
        } catch (e: SAXException) {
            throw RuntimeException(e)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    @Throws(SAXException::class, IOException::class)
    override fun getExternalSubset(name: String, baseURI: String?): InputSource {
        return InputSource(StringReader(CHAR_ENTITIES))
    }

    // --- content handler section ---------------------------------------
    @Throws(SAXException::class)
    override fun startDocument() {
        // System.out.println(locator.getLineNumber() + ":" + locator.getColumnNumber());
        setStartPosition(document)
    }

    @Throws(SAXException::class)
    override fun endDocument() {
        // System.out.println(locator.getLineNumber() + ":" + locator.getColumnNumber());
        setEndPosition(document)
    }

    @Throws(SAXException::class)
    override fun startPrefixMapping(prefix: String, uri: String) {
        super.startPrefixMapping(prefix, uri)
        prefixMap[prefix] = uri
    }

    @Throws(SAXException::class)
    override fun startElement(uri: String, localName: String, name: String, attrs: Attributes) {
        currentSection = Section.Elements
        // System.out.println(locator.getLineNumber() + ":" + locator.getColumnNumber());
        val attributes: MutableMap<String, String> =
            XmlUtils.convertSAXAttributes(attrs, preserveNameSpacePrefix)
        val element = Element(name, attributes)
        setStartPosition(element)
        if (preserveNameSpacePrefix && prefixMap.isNotEmpty()) {
            val entrySet: Set<Map.Entry<String, String>> =
                prefixMap.entries
            for ((key, value) in entrySet) {
                var attrKey = "xmlns"
                if (key.isNotEmpty()) {
                    attrKey = "$attrKey:$key"
                }
                element.setAttribute(attrKey, value)
            }
            prefixMap.clear()
        }
        val parent = elementStack.peek()
        if (parent == null) {
            document.root = element
        } else {
            parent.addNode(element)
            element.parent = parent
        }
        elementStack.push(element)
    }

    @Throws(SAXException::class)
    override fun endElement(uri: String, localName: String, name: String) {
        // System.out.println(locator.getLineNumber() + ":" + locator.getColumnNumber());
        val e = elementStack.pop()
        setEndPosition(e)
    }

    @Throws(SAXException::class)
    override fun characters(chars: CharArray, start: Int, length: Int) {
        // System.out.println(locator.getLineNumber() + ":" + locator.getColumnNumber());
        val text = Text(chars, start, length)
        addNonElementNode(text)
    }

    override fun setDocumentLocator(locator: Locator) {
        this.locator = locator
    }

    @Throws(SAXException::class)
    override fun comment(text: CharArray, start: Int, length: Int) {
        val comments = String(text, start, length)
        val comment = Comment(comments)
        addNonElementNode(comment)
    }

    @Throws(SAXException::class)
    override fun processingInstruction(target: String, data: String) {
        //    Element parent = elementStack.peek();
        val pi = ProcessingInstruction(target, data)
        addNonElementNode(pi)
    }

    private fun addNonElementNode(node: SubNode) {
        setEndPosition(node)
        val parent = elementStack.peek()
        if (parent != null) {
            parent.addNode(node)
            node.parent = parent
        } else if (currentSection == Section.HeadNodes) {
            document.headNodes.add(node)
        } else {
            currentSection = Section.FootNodes
            document.footNodes.add(node)
        }
    }

    private fun setStartPosition(node: Node) {
        node.startLine = locator!!.lineNumber
        node.startColumn = locator!!.columnNumber
    }

    private fun setEndPosition(node: Node) {
        node.endLine = locator!!.lineNumber
        node.endColumn = locator!!.columnNumber
    }

    companion object {
        // --- entity resolver section ---------------------------------------
        private const val CHAR_ENTITIES = ("""<!ENTITY mdash "&#8212;" >""" // em-dash
                + """<!ENTITY ldquo "&#8220;" >""" // left double quote
                + """<!ENTITY rdquo "&#8221;" >""" // right double quote
                + """<!ENTITY nbsp "&#160;" >""") // non-breaking space
    }
}
