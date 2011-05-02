package nl.knaw.huygens.tei;

/**
 * Ancestor for elements and text in the DOM.
 */
public abstract class Node {

  public abstract Traversal accept(Visitor visitor);

}
