package nl.knaw.huygens.tei.export

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils

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

class Files private constructor() {
    init {
        throw AssertionError("Non-instantiable class")
    }

    companion object {
        /** Default encoding for text files.  */
        private const val ENCODING: String = "UTF-8"

        /**
         * Returns a `File` with the specfied name in the user's home directory.
         * This may be a file or a directory; it may exist or not.
         */
        fun fileInHomeDirectory(filename: String): File {
            return File(System.getProperty("user.home"), filename)
        }

        @JvmOverloads
        fun writeTextToFile(text: String, file: File, append: Boolean = false) {
            var stream: FileOutputStream? = null
            try {
                stream = FileOutputStream(file, append)
                IOUtils.write(text, stream, ENCODING)
            } catch (e: IOException) {
                System.err.println(">> " + e.message)
                IOUtils.closeQuietly(stream)
            }
        }

        fun writeTextToFile(text: String, filename: String) {
            writeTextToFile(text, File(filename))
        }

        fun readTextFromFile(file: File): String {
            try {
                return FileUtils.readFileToString(file, ENCODING)
            } catch (e: IOException) {
                throw RuntimeException("Failed to read from " + file.absolutePath)
            }
        }
    }
}
