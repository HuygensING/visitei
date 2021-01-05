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

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Language-aware context. If a target language is specified only text
 * of that language will be collected, otherwise all text is selected.
 */
public class XmlContext implements Context {

  private final Deque<String> languageStack;
  private final String targetLanguage;

  private final TextLayerStack textLayerStack;
  private TextLayer textLayer;

  /**
   * Constructs a new <code>XmlContext</code>.
   *
   * @param defaultLanguage the code of the default language, "?" if unknown.
   * @param targetLanguage the code of target language, may be <code>null</code>.
   */
  public XmlContext(String defaultLanguage, String targetLanguage) {
    languageStack = new ArrayDeque<String>();
    languageStack.push(defaultLanguage);
    this.targetLanguage = targetLanguage;
    textLayerStack = new TextLayerStack();
    openLayer();
  }

  public XmlContext() {
    this("?", null);
  }

  // --- Language ------------------------------------------------------

  /** Updates the current language when a language scope starts.
   *
   *  @param element the Element
   */
  public void openLanguageScope(Element element) {
    if (element.hasLanguage()) {
      String language = element.getLanguage();
      languageStack.push(language);
    }
  }

  /** Updates the current language when a language scope ends.
   *
   *  @param element the Element
   */
  public void closeLanguageScope(Element element) {
    if (element.hasLanguage()) {
      languageStack.pop();
    }
  }

  /**
   * @return the current language.
   */
  public String getLanguage() {
    return languageStack.peek();
  }

  // -------------------------------------------------------------------

  public void openLayer() {
    textLayer = textLayerStack.openLayer();
  }

  public String closeLayer() {
    String text = textLayer.toString();
    textLayer = textLayerStack.closeLayer();
    return text;
  }

  public int getLayerLength() {
    return textLayer.length();
  }

  @Override
  public void addLiteral(Object object) {
    if (targetLanguage == null || targetLanguage.equals(languageStack.peek())) {
      textLayer.addLiteral(object);
    }
  }

  public void addComment(Object object) {
    textLayer.addComment(object.toString());
  }

  public void addOpenTag(Element element) {
    textLayer.addOpenTag(element);
  }

  public void addOpenTag(String elementName) {
    textLayer.addOpenTag(elementName);
  }

  public void addCloseTag(Element element) {
    textLayer.addCloseTag(element);
  }

  public void addCloseTag(String elementName) {
    textLayer.addCloseTag(elementName);
  }

  public void addEmptyElementTag(Element element) {
    textLayer.addEmptyElementTag(element);
  }

  public void addEmptyElementTag(String elementName) {
    textLayer.addEmptyElementTag(elementName);
  }

  public void addElement(Element element, Object content) {
    textLayer.addElement(element, content);
  }

  // -------------------------------------------------------------------

  @Override
  public String getResult() {
    return filterResult(textLayer.toString());
  }

  protected String filterResult(String text) {
    return text;
  }

}
