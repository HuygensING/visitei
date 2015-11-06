package nl.knaw.huygens.tei.handlers;

import nl.knaw.huygens.tei.Comment;
import nl.knaw.huygens.tei.CommentHandler;
import nl.knaw.huygens.tei.Traversal;
import nl.knaw.huygens.tei.XmlContext;

public class DefaultCommentHandler<T extends XmlContext> implements CommentHandler<T> {

  public DefaultCommentHandler() {}

  @Override
  public Traversal visitComment(Comment comment, T context) {
    context.addComment(comment.getComment());
    return Traversal.NEXT;
  }

}
