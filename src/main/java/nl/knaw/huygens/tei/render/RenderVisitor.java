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

import nl.knaw.huygens.tei.DelegatingVisitor;
import nl.knaw.huygens.tei.Element;
import nl.knaw.huygens.tei.XmlContext;
import nl.knaw.huygens.tei.handlers.SkipElementHandler;
import nl.knaw.huygens.tei.handlers.TransformElementHandler;

/**
 * Basisc render vistor for TEI documents.
 */
public class RenderVisitor extends DelegatingVisitor<XmlContext> {

  public RenderVisitor(UrlResolver imageUrlResolver) {
    super(new XmlContext());

    // Graphics: <figure>, <figDesc>, <graphic>
    addElementHandler(new FigureHandler(), "figure");
    addElementHandler(new SkipElementHandler<XmlContext>(), "figDesc");
    addElementHandler(new GraphicHandler(imageUrlResolver), "graphic");

    // Lists: <list>, <item>, <label>
    addElementHandler(new ListHandler(), "list", "item", "label");

    // Tables: <table>, <row>, <cell>
    addElementHandler(new TransformElementHandler("table"), "table");
    addElementHandler(new TransformElementHandler("tr"), "row");
    addElementHandler(new TableCellHandler(), "cell");

    // Remaining elements
    addElementHandler(new TransformElementHandler(Element.newDivElement("opener")), "opener");
    addElementHandler(new TransformElementHandler(Element.newDivElement("closer")), "closer");
    addElementHandler(new DivisionHandler(), "div");
    addElementHandler(new HeadHandler(), "head");
    addElementHandler(new HighlightHandler(), "hi");
    addElementHandler(new TransformElementHandler("br"), "lb");
    addElementHandler(new LineGroupHandler(), "lg", "l");
    addElementHandler(new NameHandler(), "name");
    addElementHandler(new ParagraphHandler(), "p");
    addElementHandler(new QuoteHandler(), "q");
    addElementHandler(new SegmentHandler(), "seg");
  }

  public String getResult() {
    return getContext().getResult();
  }

}
