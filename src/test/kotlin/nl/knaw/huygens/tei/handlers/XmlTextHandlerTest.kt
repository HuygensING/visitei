package nl.knaw.huygens.tei.handlers

import kotlin.test.Test
import nl.knaw.huygens.tei.DefaultContext
import nl.knaw.huygens.tei.Text

class XmlTextHandlerTest {
    @Test
    fun test() {
        val handler = XmlTextHandler<DefaultContext>()
        val context = DefaultContext()
        handler.visitText(Text("test"), context)
    }
}

