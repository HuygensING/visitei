package nl.knaw.huygens.tei;

public interface TextHandler<T extends Context> {

  Traversal visitText(Text text, T context);

}
