package nl.knaw.huygens.tei

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

/**
 * Contains definitions of some character entities.
 */
object Entities {
    const val EM_DASH: String = "&#8212;" // named: "&mdash;"

    const val LS_QUOTE: String = "&#8216;" // named: "&lsquo;"
    const val RS_QUOTE: String = "&#8217;" // named: "&rsquo;"

    const val LD_QUOTE: String = "&#8220;" // named: "&ldquo;"
    const val RD_QUOTE: String = "&#8221;" // named: "&rdquo;"

    const val BULLET: String = "&#8226;" // named: "&bull;"
}
