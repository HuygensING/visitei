package nl.knaw.huygens.tei.export;

/**
 * Collects letter text from the DOM.
 */
public class LetterTextVisitor extends TextVisitor {

  public LetterTextVisitor(String language, String targetLanguage) {
    super(language, targetLanguage);
    addElementHandler(new LetterDivisionHandler(), "div");
  }

}
