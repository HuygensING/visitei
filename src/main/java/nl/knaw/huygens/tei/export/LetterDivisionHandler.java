package nl.knaw.huygens.tei.export;

import java.util.Set;

import nl.knaw.huygens.tei.Element;
import nl.knaw.huygens.tei.Traversal;
import nl.knaw.huygens.tei.XmlContext;
import nl.knaw.huygens.tei.handlers.DefaultElementHandler;

import com.google.common.collect.Sets;

/**
 * Handles divisions in letters that are to be ignored when collecting text.
 */
public class LetterDivisionHandler extends DefaultElementHandler<XmlContext> {

  private static final Set<String> IGNORED = Sets.newHashSet("comment", "para", "summary");

  @Override
  public Traversal enterElement(Element element, XmlContext context) {
    return Traversal.stopIf(IGNORED.contains(element.getType()));
  }

}
