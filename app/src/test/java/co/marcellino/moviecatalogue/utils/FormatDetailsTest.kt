package co.marcellino.moviecatalogue.utils

import org.junit.Assert.*
import org.junit.Test

class FormatDetailsTest {

    @Test
    fun getRuntimeFormatNormal() {
        val expectedFormat = "2h 16m"

        val dummyFormat = "136 min"
        val actualFormat = FormatDetails.getRuntimeFormat(dummyFormat)

        assertNotNull(actualFormat)
        assertNotEquals("", actualFormat)
        assertEquals(expectedFormat, actualFormat)
    }

    @Test
    fun getRuntimeFormatLess() {
        val expectedFormat = "5m"

        val dummyFormat = "5 min"
        val actualFormat = FormatDetails.getRuntimeFormat(dummyFormat)

        assertNotNull(actualFormat)
        assertNotEquals("", actualFormat)
        assertEquals(expectedFormat, FormatDetails.getRuntimeFormat(dummyFormat))
    }

    @Test
    fun getGenreFormatNormal() {
        val expectedFormat = "Drama / Music / Romance"

        val dummyFormat = "Drama, Music, Romance"
        val actualFormat = FormatDetails.getGenreFormat(dummyFormat)

        assertNotNull(actualFormat)
        assertNotEquals("", actualFormat)
        assertEquals(expectedFormat, actualFormat)
    }

    @Test
    fun getGenreFormatLess() {
        val expectedFormat = "Drama"

        val dummyFormat = "Drama"
        val actualFormat = FormatDetails.getGenreFormat(dummyFormat)

        assertNotNull(actualFormat)
        assertNotEquals("", actualFormat)
        assertEquals(expectedFormat, actualFormat)
    }

    @Test
    fun getGenreFormatMore() {
        val expectedFormat = "Drama / Music / Romance"

        val dummyFormat = "Drama, Music, Romance, Comedy, Action"
        val actualFormat = FormatDetails.getGenreFormat(dummyFormat)

        assertNotNull(actualFormat)
        assertNotEquals("", actualFormat)
        assertEquals(expectedFormat, actualFormat)
    }

    @Test
    fun getCastsFormatDirector() {
        val expectedFormat = "Bradley Cooper"

        val dummyFormat = "Bradley Cooper"
        val actualFormat = FormatDetails.getCastsFormat(dummyFormat)

        assertNotNull(actualFormat)
        assertNotEquals("", actualFormat)
        assertEquals(expectedFormat, actualFormat)
    }

    @Test
    fun getCastsFormatWriter() {
        val expectedFormat =
            "Eric Roth (screenplay by)\nBradley Cooper (screenplay by)\nWill Fetters (screenplay by)"

        val dummyFormat =
            "Eric Roth (screenplay by), Bradley Cooper (screenplay by), Will Fetters (screenplay by)"
        val actualFormat = FormatDetails.getCastsFormat(dummyFormat)

        assertNotNull(actualFormat)
        assertNotEquals("", actualFormat)
        assertEquals(expectedFormat, actualFormat)
    }

    @Test
    fun getCastsFormatActors() {
        val expectedFormat = "Lady Gaga\nBradley Cooper\nSam Elliott\nAndrew Dice Clay"

        val dummyFormat = "Lady Gaga, Bradley Cooper, Sam Elliott, Andrew Dice Clay"
        val actualFormat = FormatDetails.getCastsFormat(dummyFormat)

        assertNotNull(actualFormat)
        assertNotEquals("", actualFormat)
        assertEquals(expectedFormat, actualFormat)
    }
}