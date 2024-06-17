package nl.knaw.huygens.tei.xpath;

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

import javax.xml.namespace.*;
import javax.xml.stream.*;
import javax.xml.xpath.*;
import java.io.*;
import java.util.*;
import java.util.Map.*;

import com.google.common.collect.*;
import net.sf.practicalxml.xpath.*;
import org.apache.commons.io.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class XPathUtil {

    private static Map<Class<?>, QName> returnTypes = ImmutableMap.<Class<?>, QName>builder()//
            .put(String.class, XPathConstants.STRING)//
            .put(Boolean.class, XPathConstants.BOOLEAN)//
            .put(Long.class, XPathConstants.NUMBER)//
            .put(Node.class, XPathConstants.NODE)//
            .put(NodeList.class, XPathConstants.NODESET)//
            .build();

    public static <T> T evaluate(String xpathQuery, String xml, Class<T> resultClass) throws XPathExpressionException {
        XPath xpath = XPathFactory.newInstance().newXPath();
        Map<String, String> namespaceInfo = getNamespaceInfo(xml);
        NamespaceResolver nr = new NamespaceResolver();
        for (Entry<String, String> entry : namespaceInfo.entrySet()) {
            String prefix = entry.getKey();
            String nsURI = entry.getValue();
            if (prefix.isEmpty()) {
                nr.setDefaultNamespace(nsURI);
            } else {
                nr.addNamespace(prefix, nsURI);
            }
        }
        xpath.setNamespaceContext(nr);
        if (returnTypes.containsKey(resultClass)) {
            InputSource source = new InputSource(new StringReader(xml));
            source.setEncoding("UTF-8");
            Object evaluate = xpath.evaluate(xpathQuery, source, returnTypes.get(resultClass));
            return resultClass.cast(evaluate);
        }
        throw new IllegalArgumentException("Can't return a " + resultClass.getName());
    }

    public static String evaluate(String xpathQuery, String xml) throws XPathExpressionException {
        return evaluate(xpathQuery, xml, String.class);
    }

    public static Map<String, String> getNamespaceInfo(String xml) {
        Map<String, String> namespaces = Maps.newIdentityHashMap();
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        try {
            XMLStreamReader xreader = inputFactory.createXMLStreamReader(IOUtils.toInputStream(xml, "UTF-8"));
            while (xreader.hasNext()) {
                if (xreader.next() == XMLStreamConstants.START_ELEMENT) {
                    QName qName = xreader.getName();
                    if (qName != null) {
                        addNamespace(namespaces, qName.getPrefix(), qName.getNamespaceURI());
                        for (int i = 0; i < xreader.getAttributeCount(); i++) {
                            addNamespace(namespaces, xreader.getAttributePrefix(i), xreader.getAttributeNamespace(i));
                        }
                    }
                }
            }
        } catch (javax.xml.stream.XMLStreamException | java.io.IOException e) {
            e.printStackTrace();
//    } catch (IOException e) {
//      e.printStackTrace();
        }
        return namespaces;
    }

    private static void addNamespace(Map<String, String> namespaces, String prefix, String namespace) {
        if (prefix != null) {
            if (namespace == null || "".equals(namespace)) {
                namespace = "http://ns.example.org/ns/" + prefix;
            }
            namespaces.put(prefix, namespace);
        }
    }

}
