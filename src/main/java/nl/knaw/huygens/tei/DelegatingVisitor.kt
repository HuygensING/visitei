package nl.knaw.huygens.tei;

/*
 * #%L
 * VisiTEI
 * =======
 * Copyright (C) 2011 - 2021 Huygens ING
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

import java.util.List;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import nl.knaw.huygens.tei.handlers.RenderCommentHandler;
import nl.knaw.huygens.tei.handlers.DefaultElementHandler;
import nl.knaw.huygens.tei.handlers.RenderProcessingInstructionHandler;
import nl.knaw.huygens.tei.handlers.RenderTextHandler;

public class DelegatingVisitor<T extends Context> extends DefaultVisitor {

  private final T context;
  private final Map<String, ElementHandler<T>> handlers;
  private ElementHandler<T> defaultHandler;
  private TextHandler<T> textHandler;
  private CommentHandler<T> commentHandler;
  private ProcessingInstructionHandler<T> processingInstructionHandler;

  public DelegatingVisitor(T context) {
    this.context = Preconditions.checkNotNull(context);
    handlers = Maps.newHashMap();
    defaultHandler = new DefaultElementHandler<T>();
    textHandler = new RenderTextHandler<T>();
    commentHandler = new RenderCommentHandler<T>();
    processingInstructionHandler = new RenderProcessingInstructionHandler<T>();
  }

  public T getContext() {
    return context;
  }

  public String getResult() {
    return context.getResult();
  }

  public void setTextHandler(TextHandler<T> handler) {
    textHandler = Preconditions.checkNotNull(handler);
  }

  public void setCommentHandler(CommentHandler<T> handler) {
    commentHandler = Preconditions.checkNotNull(handler);
  }

  public void setProcessingInstructionHandler(ProcessingInstructionHandler<T> handler) {
    processingInstructionHandler = Preconditions.checkNotNull(handler);
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

  public void addElementHandler(ElementHandler<T> handler, List<String> names) {
    Preconditions.checkNotNull(handler);
    for (String name : names) {
      handlers.put(name, handler);
    }
  }

  public DelegatingVisitor<T> withElementHandler(ElementHandler<T> handler, String... names) {
    addElementHandler(handler, names);
    return this;
  }

  public DelegatingVisitor<T> withElementHandler(ElementHandler<T> handler, List<String> names) {
    addElementHandler(handler, names);
    return this;
  }

  private ElementHandler<T> getElementHandler(Element element) {
    ElementHandler<T> handler = handlers.get(element.getName());
    return (handler != null) ? handler : defaultHandler;
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

  @Override
  public Traversal visitComment(Comment comment) {
    return commentHandler.visitComment(comment, context);
  }

  @Override
  public Traversal visitProcessingInstruction(ProcessingInstruction processingInstruction) {
    return processingInstructionHandler.visitProcessingInstruction(processingInstruction, context);
  }

}
