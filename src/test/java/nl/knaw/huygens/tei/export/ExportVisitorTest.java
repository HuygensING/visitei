package nl.knaw.huygens.tei.export;

/*
 * #%L
 * VisiTEI
 * =======
 * Copyright (C) 2011 - 2017 Huygens ING
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

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import nl.knaw.huygens.tei.Document;

public class ExportVisitorTest {

  private DocumentExporter exporter;

  @Before
  public void setupExporter() {
    exporter = new DefaultDocumentExporter();
  }

  private String process(String xml) {
    Document document = Document.createFromXml(xml, true);
    return exporter.apply(document);
  }

  @Test
  public void testBasicXml() {
    String xml = "<TEI><text><head><h1>Hello World</h1></head></text></TEI>";
    assertEquals(xml, process(xml));
  }

  @Test
  public void testEntities() {
    String xml = "<TEI><text>ge&lt;daen&gt;, &amp;c.</text></TEI>";
    assertEquals(xml, process(xml));
  }

  @Test
  public void testProcessingInstruction() {
    String xml = "<?oxygen RNGSchema=\"AF.rng\" type=\"xml\"?><TEI><text>text</text></TEI>";
    assertEquals(xml, process(xml));
  }

  //  @Test
  public void testCDATA() {
    String xml = "<TEI><text><![CDATA[no <escaping> necessary & needed in CDATA]]></text></TEI>";
    assertEquals(xml, process(xml));
  }

  @Test
  public void testComments() {
    String xml = "<TEI><!-- comments are preserved --></TEI>";
    assertEquals(xml, process(xml));
  }

  @Test
  public void testCommentsOutsideElementTreeGetCopiedToo() {
    String xmlIn = "<!-- first -->\n<!-- before -->\n<TEI><!-- inside --></TEI>\n<!-- after -->\n<!-- last -->";
    String xmlOut = xmlIn.replace("\n", "");
    assertEquals(xmlOut, process(xmlIn));
  }

  @Test
  public void testDontSplitUpText() {
    String xml = "<text><div type=\"opener\" resp=\"WR\"><p>Illustrissime Domine legate,</p></div></text>";
    assertEquals(xml, process(xml));
  }

  @Test
  public void testComplexXml() {
    // File has UNIX style EOL's.
    File file = new File("data/test/grotius/a0001.xml");
    String xml = Files.readTextFromFile(file).trim();
    // Normalize order of attributes
    String normalized = process(xml);
    assertEquals(xml.length(), normalized.length());
    // Normalized xml should be idempotent
    assertEquals(normalized, process(normalized));
  }

}
