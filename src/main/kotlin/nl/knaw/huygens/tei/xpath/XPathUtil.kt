package nl.knaw.huygens.tei.xpath

import java.io.IOException
import java.io.StringReader
import javax.xml.namespace.QName
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.XMLStreamConstants
import javax.xml.stream.XMLStreamException
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathExpressionException
import javax.xml.xpath.XPathFactory
import com.google.common.collect.ImmutableMap
import com.google.common.collect.Maps
import net.sf.practicalxml.xpath.NamespaceResolver
import org.apache.commons.io.IOUtils
import org.w3c.dom.Node
import org.w3c.dom.NodeList
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

object XPathUtil {
    private val returnTypes: Map<Class<*>, QName> = ImmutableMap.builder<Class<*>, QName>() //
        .put(String::class.java, XPathConstants.STRING) //
        .put(Boolean::class.java, XPathConstants.BOOLEAN) //
        .put(Long::class.java, XPathConstants.NUMBER) //
        .put(Node::class.java, XPathConstants.NODE) //
        .put(NodeList::class.java, XPathConstants.NODESET) //
        .build()

    @Throws(XPathExpressionException::class)
    fun <T> evaluate(xpathQuery: String, xml: String, resultClass: Class<T>): T {
        val xpath = XPathFactory.newInstance().newXPath()
        val namespaceInfo = getNamespaceInfo(xml)
        val nr = NamespaceResolver()
        for ((prefix, nsURI) in namespaceInfo) {
            if (prefix.isEmpty()) {
                nr.setDefaultNamespace(nsURI)
            } else {
                nr.addNamespace(prefix, nsURI)
            }
        }
        xpath.namespaceContext = nr
        if (returnTypes.containsKey(resultClass)) {
            val source = InputSource(StringReader(xml))
            source.encoding = "UTF-8"
            val evaluate = xpath.evaluate(xpathQuery, source, returnTypes[resultClass])
            return resultClass.cast(evaluate)
        }
        throw IllegalArgumentException("Can't return a " + resultClass.name)
    }

    @Throws(XPathExpressionException::class)
    fun evaluate(xpathQuery: String, xml: String): String {
        return evaluate(xpathQuery, xml, String::class.java)
    }

    fun getNamespaceInfo(xml: String): Map<String, String> {
        val namespaces: MutableMap<String, String> = Maps.newIdentityHashMap()
        val inputFactory = XMLInputFactory.newInstance()
        try {
            val xreader = inputFactory.createXMLStreamReader(IOUtils.toInputStream(xml, "UTF-8"))
            while (xreader.hasNext()) {
                if (xreader.next() == XMLStreamConstants.START_ELEMENT) {
                    val qName = xreader.name
                    if (qName != null) {
                        addNamespace(namespaces, qName.prefix, qName.namespaceURI)
                        for (i in 0 until xreader.attributeCount) {
                            addNamespace(namespaces, xreader.getAttributePrefix(i), xreader.getAttributeNamespace(i))
                        }
                    }
                }
            }
        } catch (e: XMLStreamException) {
            e.printStackTrace()
            //    } catch (IOException e) {
//      e.printStackTrace();
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return namespaces
    }

    private fun addNamespace(namespaces: MutableMap<String, String>, prefix: String, namespace: String) {
        var namespace: String = namespace
        if (prefix != null) {
            if (namespace == null || "" == namespace) {
                namespace = "http://ns.example.org/ns/$prefix"
            }
            namespaces[prefix] = namespace
        }
    }
}
