package nl.knaw.huygens.tei

import java.io.StringReader
import javax.xml.xpath.XPath
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathExpressionException
import javax.xml.xpath.XPathFactory
import net.sf.practicalxml.xpath.NamespaceResolver
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import nl.knaw.huygens.tei.xpath.XPathUtil

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

class QueryableDocument private constructor(private val document: Document) {
    private var xml: String? = null
    private var needsPrefix = false
    var defaultPrefix: String = DEFAULTPREFIX
    private val xpath: XPath = XPathFactory.newInstance().newXPath()

    //  private static String calculateDefaultPrefix(List<String> allPrefixes) {
    //    String prefix = DEFAULTPREFIX;
    //    int i = 0;
    //    while (allPrefixes.contains(prefix)) {
    //      prefix = DEFAULTPREFIX + i++;
    //    }
    //    return prefix;
    //  }
    private fun setNamespaceContext(nr: NamespaceResolver) {
        xpath.namespaceContext = nr
    }

    fun setXml(xml: String) {
        this.xml = xml
    }

    fun needsPrefix(): Boolean {
        return needsPrefix
    }

    fun setNeedsPrefix(needsPrefix: Boolean) {
        this.needsPrefix = needsPrefix
    }

    // --- XPath ------------------------------------------------------
    @Throws(XPathExpressionException::class)
    fun evaluateXPathToString(xpathQuery: String): String {
        return xpath.evaluate(xpathQuery, inputSource(), XPathConstants.STRING) as String
    }

    @Throws(XPathExpressionException::class)
    fun evaluateXPathToDouble(xpathQuery: String): Double {
        return xpath.evaluate(xpathQuery, inputSource(), XPathConstants.NUMBER) as Double
    }

    @Throws(XPathExpressionException::class)
    fun evaluateXPathToBoolean(xpathQuery: String): Boolean {
        return xpath.evaluate(xpathQuery, inputSource(), XPathConstants.BOOLEAN) as Boolean
    }

    @Throws(XPathExpressionException::class)
    fun evaluateXPathToW3CNodeList(xpathQuery: String): NodeList {
        return xpath.evaluate(xpathQuery, inputSource(), XPathConstants.NODESET) as NodeList
    }

    private fun inputSource(): InputSource {
        return InputSource(StringReader(xml))
    }

    val startLine: Int
        // --- delegated methods -------------------------------------
        get() = document.startLine

    val startColumn: Int
        get() = document.startColumn

    val endLine: Int
        get() = document.endLine

    val endColumn: Int
        get() = document.endColumn

    var root: Element?
        get() = document.root
        set(element) {
            document.root = element
        }

    val headNodes: List<Node>
        get() = document.headNodes

    val footNodes: List<Node>
        get() = document.footNodes

    fun getElementsByTagName(name: String): List<Element> {
        return document.getElementsByTagName(name)
    }

    fun accept(visitor: Visitor): Traversal {
        return document.accept(visitor)
    }

    companion object {
        private const val DEFAULTPREFIX = "ns"
        fun createFromXml(xml: String, preserveNamespacePrefix: Boolean): QueryableDocument {
            val namespaceResolver = NamespaceResolver()
            for (entry in XPathUtil.getNamespaceInfo(xml).entries) {
                val prefix = entry.key
                var nsURI = entry.value
                if (prefix != "") {
                    if ("" == nsURI) {
                        nsURI = "http://example.com/ns"
                    }
                    namespaceResolver.addNamespace(prefix, nsURI)
                }
            }

            val qDocument = QueryableDocument(Document.createFromXml(xml, preserveNamespacePrefix))
            qDocument.setXml(removeDefaultNamespace(xml))
            if (namespaceResolver.allPrefixes.isNotEmpty()) {
                qDocument.setNamespaceContext(namespaceResolver)
            }
            //    List<String> allPrefixes = namespaceResolver.getAllPrefixes();
            //    if (!allPrefixes.isEmpty()) {
            //      qDocument.setNeedsPrefix(true);
            //      String defaultPrefix = calculateDefaultPrefix(allPrefixes);
            //      namespaceResolver.addNamespace(defaultPrefix, namespaceResolver.getNamespaceURI(""));
            //      qDocument.setDefaultPrefix(defaultPrefix);
            //    }
            return qDocument
        }

        private fun removeDefaultNamespace(xml: String): String {
            return xml.replaceFirst(" xmlns=\"[^\"]*\"".toRegex(), "")
        }
    }
}
