package nl.knaw.huygens.tei.render;

import nl.knaw.huygens.tei.Element;
import nl.knaw.huygens.tei.ElementHandler;
import nl.knaw.huygens.tei.Entities;
import nl.knaw.huygens.tei.Traversal;
import nl.knaw.huygens.tei.XmlContext;

/**
 * Renders the TEI <code>q</code> element.
 */
public class QuoteHandler implements ElementHandler<XmlContext> {

  private static final Element BLOCK_QUOTE = Element.newDivElement("blockquote");

  private boolean isBlockQuote(Element element) {
    return element.hasRendition("bq");
  }

  @Override
  public Traversal enterElement(Element element, XmlContext context) {
    if (isBlockQuote(element)) {
      context.addOpenTag(BLOCK_QUOTE);
    } else {
      context.addLiteral(Entities.LS_QUOTE);
    }
    return Traversal.NEXT;
  }

  @Override
  public Traversal leaveElement(Element element, XmlContext context) {
    if (isBlockQuote(element)) {
      context.addCloseTag(BLOCK_QUOTE);
    } else {
      context.addLiteral(Entities.RS_QUOTE);
    }
    return Traversal.NEXT;
  }

}
