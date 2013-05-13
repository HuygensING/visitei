package nl.knaw.huygens.tei.handlers;

import nl.knaw.huygens.tei.XmlContext;

public class XmlTextHandler extends DefaultTextHandler<XmlContext> {

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
    //    return StringEscapeUtils.escapeXml(text)
  }
}
