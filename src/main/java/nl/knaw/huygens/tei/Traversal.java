package nl.knaw.huygens.tei;

/**
 * Formalizes the notion what to do when traversing the DOM.
 */
public enum Traversal {
  NEXT, STOP;

  public static Traversal nextIf(boolean condition) {
    return condition ? Traversal.NEXT : Traversal.STOP;
  }

  public static Traversal stopIf(boolean condition) {
    return condition ? Traversal.STOP : Traversal.NEXT;
  }

}
