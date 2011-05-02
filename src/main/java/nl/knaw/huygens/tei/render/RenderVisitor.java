package nl.knaw.huygens.tei.render;

import nl.knaw.huygens.tei.DelegatingVisitor;
import nl.knaw.huygens.tei.Element;
import nl.knaw.huygens.tei.XmlContext;
import nl.knaw.huygens.tei.handlers.FilterElementHandler;
import nl.knaw.huygens.tei.handlers.TransformElementHandler;

/**
 * Basisc render vistor for TEI documents.
 */
public class RenderVisitor extends DelegatingVisitor<XmlContext> {

  public RenderVisitor(UrlResolver imageUrlResolver) {
    super(new XmlContext());

    // Graphics: <figure>, <figDesc>, <graphic>
    addElementHandler(new FigureHandler(), "figure");
    addElementHandler(new FilterElementHandler(), "figDesc");
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

}
