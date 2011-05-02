package nl.knaw.huygens.tei.export;

import nl.knaw.huygens.tei.DelegatingVisitor;
import nl.knaw.huygens.tei.XmlContext;

/**
 * Collects text from the DOM.
 */
public class TextVisitor extends DelegatingVisitor<XmlContext> {

  public TextVisitor(String language, String targetLanguage) {
    super(new XmlContext(language, targetLanguage) {
      @Override
      protected String filterResult(String text) {
        // Remove brackets and parentheses
        text = text.replaceAll("[\\[\\]\\(\\)]", "");
        text = text.replaceAll("&nbsp;", " ");
        // Normalize spaces and tabs
        text = text.replaceAll("[ \\t]+", " ");
        return text.trim();
      }
    });

    addElementHandler(new LanguageHandler(), "p");
  }

}
