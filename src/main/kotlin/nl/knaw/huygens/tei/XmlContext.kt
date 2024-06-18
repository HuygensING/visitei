package nl.knaw.huygens.tei

import java.util.ArrayDeque
import java.util.Deque

/*
* #%L
 * VisiTEI
 * =======
 * Copyright (C) 2011 - 2024 Huygens ING
 * =======
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public
 *  License along with this program.  If not, see
 *  <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
*/

/**
 * Language-aware context. If a target language is specified only text
 * of that language will be collected, otherwise all text is selected.
 */
class XmlContext @JvmOverloads constructor(
    defaultLanguage: String = "?",
    targetLanguage: String? = null
) : Context {
    private val languageStack: Deque<String> = ArrayDeque()
    private val targetLanguage: String?

    private val textLayerStack: TextLayerStack
    private var textLayer: TextLayer? = null

    /**
     * Constructs a new `XmlContext`.
     *
     * @param defaultLanguage the code of the default language, "?" if unknown.
     * @param targetLanguage the code of target language, may be `null`.
     */
    init {
        languageStack.push(defaultLanguage)
        this.targetLanguage = targetLanguage
        textLayerStack = TextLayerStack()
        openLayer()
    }

    // --- Language ------------------------------------------------------
    /** Updates the current language when a language scope starts.
     *
     * @param element the Element
     */
    fun openLanguageScope(element: Element) {
        if (element.hasLanguage()) {
            val language = element.language
            languageStack.push(language)
        }
    }

    /** Updates the current language when a language scope ends.
     *
     * @param element the Element
     */
    fun closeLanguageScope(element: Element) {
        if (element.hasLanguage()) {
            languageStack.pop()
        }
    }

    val language: String?
        /**
         * @return the current language.
         */
        get() = languageStack.peek()

    // -------------------------------------------------------------------
    fun openLayer() {
        textLayer = textLayerStack.openLayer()
    }

    fun closeLayer(): String {
        val text = textLayer.toString()
        textLayer = textLayerStack.closeLayer()
        return text
    }

    val layerLength: Int
        get() = textLayer!!.length()

    override fun addLiteral(`object`: Any) {
        if (targetLanguage == null || targetLanguage == languageStack.peek()) {
            textLayer!!.addLiteral(`object`)
        }
    }

    fun addComment(`object`: Any) {
        textLayer!!.addComment(`object`.toString())
    }

    fun addOpenTag(element: Element) {
        textLayer!!.addOpenTag(element)
    }

    fun addOpenTag(elementName: String) {
        textLayer!!.addOpenTag(elementName)
    }

    fun addCloseTag(element: Element) {
        textLayer!!.addCloseTag(element)
    }

    fun addCloseTag(elementName: String) {
        textLayer!!.addCloseTag(elementName)
    }

    fun addEmptyElementTag(element: Element) {
        textLayer!!.addEmptyElementTag(element)
    }

    fun addEmptyElementTag(elementName: String) {
        textLayer!!.addEmptyElementTag(elementName)
    }

    fun addElement(element: Element, content: Any) {
        textLayer!!.addElement(element, content)
    }

    override val result: String
        // -------------------------------------------------------------------
        get() = filterResult(textLayer.toString())

    private fun filterResult(text: String): String {
        return text
    }
}
