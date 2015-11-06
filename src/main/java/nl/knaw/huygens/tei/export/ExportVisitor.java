package nl.knaw.huygens.tei.export;

import nl.knaw.huygens.tei.DelegatingVisitor;
import nl.knaw.huygens.tei.XmlContext;
import nl.knaw.huygens.tei.handlers.DefaultTextHandler;
import nl.knaw.huygens.tei.handlers.RenderElementHandler;

/**
 * Converts a DOM to xml.
 * 
 * Notes:
 * - attributes of elements may be reordered.
 * - the internal format of Java strings uses (Unix) line endings '\n',
 * hence the exported format also uses these.
 * - the result may differ in whitespace after the close of the root element.
 * - only three entities are handled.
 */
public class ExportVisitor extends DelegatingVisitor<XmlContext> {

  public ExportVisitor() {
    super(new XmlContext());
    setDefaultElementHandler(new RenderElementHandler());
    setTextHandler(new DefaultTextHandler<XmlContext>() {
      @Override
      protected String filterText(String text) {
        int n = text.length();
        StringBuilder builder = new StringBuilder((int) (n * 1.1));
        for (int i = 0; i < n; i++) {
          char c = text.charAt(i);
          switch (c) {
          case '<':
            builder.append("&lt;");
            break;
          case '>':
            builder.append("&gt;");
            break;
          case '&':
            builder.append("&amp;");
            break;
          default:
            builder.append(c);
            break;
          }
        }
        return builder.toString();
      }
    });
  }

}
