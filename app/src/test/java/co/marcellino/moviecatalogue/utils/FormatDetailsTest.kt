package co.marcellino.moviecatalogue.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class FormatDetailsTest {

    @Test
    fun getRuntimeFormatNormal() {
        val expectedFormat = "2h 16m"

        val dummyFormat = "136 min"
        assertEquals(expectedFormat, FormatDetails.getRuntimeFormat(dummyFormat))
    }

    @Test
    fun getRuntimeFormatLess() {
        val expectedFormat = "5m"

        val dummyFormat = "5 min"
        assertEquals(expectedFormat, FormatDetails.getRuntimeFormat(dummyFormat))
    }

    @Test
    fun getGenreFormatNormal() {
        val expectedFormat = "Drama / Music / Romance"

        val dummyFormat = "Drama, Music, Romance"
        assertEquals(expectedFormat, FormatDetails.getGenreFormat(dummyFormat))
    }

    @Test
    fun getGenreFormatLess() {
        val expectedFormat = "Drama"

        val dummyFormat = "Drama"
        assertEquals(expectedFormat, FormatDetails.getGenreFormat(dummyFormat))
    }

    @Test
    fun getGenreFormatMore() {
        val expectedFormat = "Drama / Music / Romance"

        val dummyFormat = "Drama, Music, Romance, Comedy, Action"
        assertEquals(expectedFormat, FormatDetails.getGenreFormat(dummyFormat))
    }

    @Test
    fun getCastsFormatDirector() {
        val expectedFormat = "Bradley Cooper"

        val dummyFormat = "Bradley Cooper"
        assertEquals(expectedFormat, FormatDetails.getCastsFormat(dummyFormat))
    }

    @Test
    fun getCastsFormatWriter() {
        val expectedFormat =
            "Eric Roth (screenplay by)\nBradley Cooper (screenplay by)\nWill Fetters (screenplay by)"

        val dummyFormat =
            "Eric Roth (screenplay by), Bradley Cooper (screenplay by), Will Fetters (screenplay by)"
        assertEquals(expectedFormat, FormatDetails.getCastsFormat(dummyFormat))
    }

    @Test
    fun getCastsFormatActors() {
        val expectedFormat = "Lady Gaga\nBradley Cooper\nSam Elliott\nAndrew Dice Clay"

        val dummyFormat = "Lady Gaga, Bradley Cooper, Sam Elliott, Andrew Dice Clay"
        assertEquals(expectedFormat, FormatDetails.getCastsFormat(dummyFormat))
    }
}