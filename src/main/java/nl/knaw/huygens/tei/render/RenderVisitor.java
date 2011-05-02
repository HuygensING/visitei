package nl.knaw.huygens.tei.render;

import nl.knaw.huygens.tei.DelegatingVisitor;
import nl.knaw.huygens.tei.Element;
import nl.knaw.huygens.tei.XmlContext;
import nl.knaw.huygens.tei.handlers.FilterElementHandler;
import nl.knaw.huygens.tei.handlers.TransformElementHandler;

/**
 * TEI elements in current corpus (as of 2011-03-15):
 * <pre>
 * body                      18427
 * cell                      14396
 * closer                     8395
 * date                       7763
 * div-t:appendix               63
 * div-t:comment               133
 * div-t:letter              18351
 * div-t:para                 6678
 * div-t:section               304
 * div-t:summary               168
 * figDesc                     115
 * figure                     1209
 * figure-r:inline             731
 * graphic                    1942
 * head                      11094
 * head-r:h3                  7297
 * head-r:h4                   551
 * hi-r:b                       21
 * hi-r:i                    38330
 * hi-r:sc                     182
 * hi-r:spat                    70
 * hi-r:sub                     43
 * hi-r:sup                  21736
 * item                        557
 * l                          2509
 * l-r:; 1tab                  535
 * l-r:; 2tab                   27
 * label                       267
 * lb                        12841
 * lg-t:poem                   653
 * list                        113
 * name-t:bible                250
 * name-t:person              4076
 * name-t:topo                1752
 * num-t:roman                 106
 * opener                     2093
 * p                        123608
 * q-r:bq                      130
 * row                        2751
 * seg-t:corr                    2
 * seg-t:incipit              7868
 * seg-t:math                   95
 * seg-t:signed               5988
 * table                       281
 * title                       137
 * </pre>
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
