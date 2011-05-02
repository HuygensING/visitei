package nl.knaw.huygens.tei;

import java.util.Map;

import nl.knaw.huygens.tei.handlers.DefaultElementHandler;
import nl.knaw.huygens.tei.handlers.DefaultTextHandler;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

public class DelegatingVisitor<T extends Context> extends DefaultVisitor {

  private final T context;
  private final Map<String, ElementHandler<T>> handlers;
  private ElementHandler<T> defaultHandler;
  private TextHandler<T> textHandler;

  public DelegatingVisitor(T context) {
    this.context = context;
    handlers = Maps.newHashMap();
    defaultHandler = new DefaultElementHandler<T>();
    textHandler = new DefaultTextHandler<T>();
  }

  public T getContext() {
    return context;
  }

  public void setDefaultElementHandler(ElementHandler<T> handler) {
    defaultHandler = Preconditions.checkNotNull(handler);
  }

  public void addElementHandler(ElementHandler<T> handler, String... names) {
    Preconditions.checkNotNull(handler);
    for (String name : names) {
      handlers.put(name, handler);
    }
  }

  private ElementHandler<T> getElementHandler(Element element) {
    ElementHandler<T> handler = handlers.get(element.getName());
    return (handler != null) ? handler : defaultHandler;
  }

  public void setTextHandler(TextHandler<T> handler) {
    textHandler = handler;
  }

  // --- Visiting ------------------------------------------------------

  @Override
  public Traversal enterElement(Element element) {
    return getElementHandler(element).enterElement(element, context);
  }

  @Override
  public Traversal leaveElement(Element element) {
    return getElementHandler(element).leaveElement(element, context);
  }

  @Override
  public Traversal visitText(Text text) {
    return textHandler.visitText(text, context);
  }

}
