package nl.knaw.huygens.tei.render;

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

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import nl.knaw.huygens.tei.DelegatingVisitor;
import nl.knaw.huygens.tei.Document;
import nl.knaw.huygens.tei.Documents;
import nl.knaw.huygens.tei.XmlContext;

public class SegmentHandlerTest {

  private String process(String xml) {
    Document document = Documents.newDocument(xml);
    DelegatingVisitor<XmlContext> visitor = new DelegatingVisitor<XmlContext>(new XmlContext());
    visitor.addElementHandler(new SegmentHandler(), "seg");
    document.accept(visitor);
    return visitor.getResult();
  }

  @Test
  public void testNoSegment() {
    assertEquals("text", process("<hi>text</hi>"));
  }

  @Test
  public void testSegmentWithoutType() {
    assertEquals("text", process("<seg>text</seg>"));
  }

  @Test
  public void testSegmentWithRend() {
    assertEquals("<span class=\"x\">text</span>", process("<seg type=\"x\">text</seg>"));
  }

}
