package nl.knaw.huygens.tei;

import java.util.ArrayDeque;
import java.util.Deque;

public class TextLayerStack {

  private final Deque<TextLayer> stack;

  public TextLayerStack() {
    stack = new ArrayDeque<TextLayer>(4);
  }

  public TextLayer openLayer() {
    TextLayer stream = new TextLayer();
    stack.push(stream);
    return stream;
  }

  public TextLayer closeLayer() {
    if (stack.size() == 0) {
      throw new IllegalStateException("Layer stack is empty");
    }
    stack.pop();
    return stack.peek();
  }

}
