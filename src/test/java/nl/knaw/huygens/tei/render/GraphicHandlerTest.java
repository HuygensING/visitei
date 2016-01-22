package nl.knaw.huygens.tei.render;

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
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import nl.knaw.huygens.tei.DelegatingVisitor;
import nl.knaw.huygens.tei.Document;
import nl.knaw.huygens.tei.XmlContext;

public class GraphicHandlerTest {

  private static class TestResolver implements UrlResolver {
    @Override
    public String resolve(String key) {
      return "base/" + key;
    }
  }

  private String process(String xml) {
    Document document = Document.createFromXml(xml);
    DelegatingVisitor<XmlContext> visitor = new DelegatingVisitor<XmlContext>(new XmlContext());
    visitor.addElementHandler(new GraphicHandler(new TestResolver()), "graphic");
    document.accept(visitor);
    return visitor.getContext().getResult();
  }

  @Test
  public void testNoGraphic() {
    assertEquals("text", process("<seg>text</seg>"));
  }

  @Test
  public void testGraphicWithoutUrl() {
    assertEquals("", process("<graphic id=\"test.gif\"/>"));
  }

  @Test
  public void testGraphic() {
    // N.B. Order of attributes depends on implementation
    String result = process("<graphic url=\"test.gif\"/>");
    assertTrue(result.startsWith("<img "));
    assertTrue(result.contains(" alt=\"[test.gif]\""));
    assertTrue(result.contains(" src=\"base/test.gif\""));
    assertTrue(result.endsWith("/>"));
  }

  @Test
  public void testGraphicWithContent() {
    // N.B. Order of attributes depends on implementation
    String result = process("<graphic url=\"test.gif\">text</graphic>");
    assertTrue(result.startsWith("<img "));
    assertTrue(result.contains(" alt=\"[test.gif]\""));
    assertTrue(result.contains(" src=\"base/test.gif\""));
    assertTrue(result.endsWith("/>"));
  }

}
