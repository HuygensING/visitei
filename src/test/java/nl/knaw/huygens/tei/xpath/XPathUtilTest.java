package nl.knaw.huygens.tei.xpath;

/*
 * #%L
 * VisiTEI
 * =======
 * Copyright (C) 2011 - 2016 Huygens ING
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

import static org.junit.Assert.assertEquals;

import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import org.junit.Test;

import nl.knaw.huygens.Log;
import nl.knaw.huygens.tei.QueryableDocument;

public class XPathUtilTest {

  @Test
  public void testNamespaceExtraction() {
    String xml = "<a xmlns=\"http://ns.example.com/whatever\" xmlns:a=\"http://ns.example.com/whatever/a\">whaterver, <a:person>dude</a:person></a>";
    Map<String, String> namespaceInfo = XPathUtil.getNamespaceInfo(xml);
    Log.info("namespaces = {}", namespaceInfo);
  }

  @Test
  public void testDocumentXPathEvaluation() throws XPathExpressionException {
    String xml = "<a xmlns=\"http://ns.example.com/whatever\" xmlns:a=\"http://ns.example.com/whatever/a\">whaterver, <a:person>dude</a:person></a>";
    QueryableDocument doc = QueryableDocument.createFromXml(xml, true);
    String result = doc.evaluateXPathToString("//a:person");
    assertEquals("dude", result);
  }

  @Test
  public void testDocumentXPathEvaluationWithXmlId() throws XPathExpressionException {
    String xml = "<a>whatever, <div xml:id=\"d0\">dude</div></a>";
    QueryableDocument doc = QueryableDocument.createFromXml(xml, true);
    String result = doc.evaluateXPathToString("//div[@xml:id='d0']");
    assertEquals("dude", result);
  }
}
