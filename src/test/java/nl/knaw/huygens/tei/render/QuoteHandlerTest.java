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

public class QuoteHandlerTest {

  private String process(String xml) {
    Document document = Documents.newDocument(xml);
    DelegatingVisitor<XmlContext> visitor = new DelegatingVisitor<XmlContext>(new XmlContext());
    visitor.addElementHandler(new QuoteHandler(), "q");
    document.accept(visitor);
    return visitor.getResult();
  }

  @Test
  public void testNoQuote() {
    assertEquals("text", process("<seg>text</seg>"));
  }

  @Test
  public void testPlainQuote() {
    assertEquals("&#8216;text&#8217;", process("<q>text</q>"));
  }

  @Test
  public void testOtherQuote() {
    assertEquals("&#8216;text&#8217;", process("<q rend=\"x\">text</q>"));
  }

  @Test
  public void testBlockQuote() {
    assertEquals("<div class=\"blockquote\">text</div>", process("<q rend=\"bq\">text</q>"));
  }

}
