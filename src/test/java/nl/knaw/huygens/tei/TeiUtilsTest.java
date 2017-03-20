package nl.knaw.huygens.tei;

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

import java.util.List;

import org.junit.Test;
import  org.junit.Assert;

public class TeiUtilsTest {

  private Document getTestDocument() {
    String xml = "<root><a n=\"1\"><a n=\"2\"><x/></a></a><y/></root>";
    return Document.createFromXml(xml, false);
  }

  @Test
  public void testGetAncestorWithMissingParent() {
    Document document = getTestDocument();
    List<Element> elements = document.getElementsByTagName("y");
    Assert.assertEquals(1, elements.size());
    Element element = elements.get(0);
    Element ancestor = TeiUtils.getAncestor(element, "a");
    Assert.assertNull(ancestor);
  }

  @Test
  public void testGetAncestorWithParent() {
    Document document = getTestDocument();
    List<Element> elements = document.getElementsByTagName("x");
    Assert.assertEquals(1, elements.size());
    Element element = elements.get(0);
    Element ancestor = TeiUtils.getAncestor(element, "a");
    Assert.assertNotNull(ancestor);
    Assert.assertTrue(ancestor.hasName("a") && ancestor.hasAttribute("n", "2"));
  }
 
}
