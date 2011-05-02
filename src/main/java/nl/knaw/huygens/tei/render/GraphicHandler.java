package nl.knaw.huygens.tei.render;

import nl.knaw.huygens.tei.Element;
import nl.knaw.huygens.tei.Traversal;
import nl.knaw.huygens.tei.XmlContext;
import nl.knaw.huygens.tei.handlers.DefaultElementHandler;

/**
 * Renders the TEI <code>graphic</code> element.
 */
public class GraphicHandler extends DefaultElementHandler<XmlContext> {

  private UrlResolver urlResolver;

  public GraphicHandler(UrlResolver resolver) {
    urlResolver = resolver;
  }

  @Override
  public Traversal enterElement(Element element, XmlContext context) {
    String url = element.getAttribute("url");
    if (url.length() != 0) {
      Element rendered = new Element("img");
      rendered.setAttribute("src", urlResolver.resolve(url));
      rendered.setAttribute("alt", "[" + url + "]");
      context.addEmptyElementTag(rendered);
    }
    return Traversal.STOP;
  }

}
