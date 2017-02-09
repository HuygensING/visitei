package nl.knaw.huygens.tei.render;

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
import org.junit.Test;
import nl.knaw.huygens.tei.DelegatingVisitor;
import nl.knaw.huygens.tei.Document;
import nl.knaw.huygens.tei.Documents;
import nl.knaw.huygens.tei.XmlContext;

public class HeadHandlerTest {

  private String process(String xml) {
    Document document = Documents.newDocument(xml);
    DelegatingVisitor<XmlContext> visitor = new DelegatingVisitor<XmlContext>(new XmlContext());
    visitor.addElementHandler(new HeadHandler(), "head");
    document.accept(visitor);
    return visitor.getResult();
  }

  @Test
  public void testHeadWithoutRend() {
    assertEquals("<h3>text</h3>", process("<div type=\"section\"><head>text</head></div>"));
  }

  @Test
  public void testHeadWithRend() {
    assertEquals("<h4>text</h4>", process("<div type=\"section\"><head rend=\"h4\">text</head></div>"));
  }

  @Test
  public void testHeadWithLang() {
    assertEquals("<h3 lang=\"la\">text</h3>", process("<div><head lang=\"la\">text</head></div>"));
  }

}
