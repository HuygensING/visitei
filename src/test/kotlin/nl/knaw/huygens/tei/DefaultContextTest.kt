package nl.knaw.huygens.tei

import kotlin.test.assertEquals
import org.junit.Test

class DefaultContextTest {
    @Test
    fun `result is empty`() {
        val context = DefaultContext()
        assertEquals("", context.result)
    }
}