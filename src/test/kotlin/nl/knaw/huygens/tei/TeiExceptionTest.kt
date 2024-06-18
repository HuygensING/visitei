package nl.knaw.huygens.tei

import kotlin.test.*
import org.junit.Test

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

class TeiExceptionTest {
    @Test(expected = Exception::class)
    @Throws(TeiException::class)
    fun testThrowing() {
        throw TeiException()
    }

    @Test
    fun testFormatting() {
        val e = TeiException("Test %d", 1)
        assertEquals("Test 1", e.message)
    }

    @Test
    fun testConstructor() {
        val e = TeiException("Test")
        assertEquals("Test", e.message)
    }
}
