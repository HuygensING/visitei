package nl.knaw.huygens.tei;

public interface CommentHandler<T extends XmlContext> {

  Traversal visitComment(Comment comment, T context);

}
