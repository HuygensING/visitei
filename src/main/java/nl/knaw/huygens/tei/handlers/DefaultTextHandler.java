package nl.knaw.huygens.tei.handlers;

import nl.knaw.huygens.tei.Context;
import nl.knaw.huygens.tei.Text;
import nl.knaw.huygens.tei.TextHandler;
import nl.knaw.huygens.tei.Traversal;

public class DefaultTextHandler<T extends Context> implements TextHandler<T> {

  public DefaultTextHandler() {}

  @Override
  public Traversal visitText(Text text, T context) {
    String content = filterText(text.getText());
    context.addLiteral(content);
    return Traversal.NEXT;
  }

  protected String filterText(String content) {
    return content;
  }

}
