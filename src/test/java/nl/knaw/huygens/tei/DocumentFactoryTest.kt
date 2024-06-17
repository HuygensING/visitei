package nl.knaw.huygens.tei;

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

import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class DocumentFactoryTest {

  @Test
  public void testXMLNSAttributesAreHandledCorrectly() {
    String xml = "<TEI xmlns=\"http://www.tei-c.org/ns/1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.tei-c.org/ns/1.0 http://www.tei-c.org/release/xml/tei/custom/schema/xsd/tei_all.xsd\"></TEI>";
    Document doc = Document.createFromXml(xml, true);
    List<Element> elements = doc.getElementsByTagName("TEI");
    Assert.assertEquals(elements.size(), 1);
    Element tei = elements.get(0);
    Set<String> attributeNames = tei.getAttributeNames();
    Assert.assertTrue(attributeNames.contains("xmlns:xsi"));
    Assert.assertTrue(attributeNames.contains("xsi:schemaLocation"));
    Assert.assertTrue(attributeNames.contains("xmlns"));
  }

}
