package co.marcellino.moviecatalogue.utils

import java.io.IOException
import java.io.InputStream

object RawReader {

    fun readFromRaw(inputStream: InputStream): String? {
        return try {
            inputStream.bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            null
        }
    }
}