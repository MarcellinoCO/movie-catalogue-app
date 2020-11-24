package co.marcellino.moviecatalogue.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import co.marcellino.moviecatalogue.data.source.remote.RemoteDataSource
import co.marcellino.moviecatalogue.data.source.remote.response.movie.MovieDetailsResponse
import co.marcellino.moviecatalogue.data.source.remote.response.show.ShowDetailsResponse
import co.marcellino.moviecatalogue.utils.LiveDataTestUtil
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class RepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val repository = FakeRepository(remote)

    private val movieResponses = listOf(MovieDetailsResponse())
    private val showResponses = listOf(ShowDetailsResponse())

    @Test
    fun discoverMovies() {
        doAnswer { invocationOnMock ->
            (invocationOnMock.arguments[0] as RemoteDataSource.LoadMoviesCallback).onMoviesReceived(
                movieResponses
            )
            null
        }.`when`(remote).discoverMovies(any())

        val movieEntities = LiveDataTestUtil.getValue(repository.discoverMovies())
        verify(remote).discoverMovies(any())

        assertNotNull(movieEntities)
        assertEquals(movieResponses.size, movieEntities.size)
    }

    @Test
    fun discoverMoviesError() {
        doAnswer { invocationOnMock ->
            (invocationOnMock.arguments[0] as RemoteDataSource.LoadMoviesCallback).onFailure()
            null
        }.`when`(remote).discoverMovies(any())

        val movieEntities = LiveDataTestUtil.getValue(repository.discoverMovies())
        verify(remote).discoverMovies(any())

        assertNull(movieEntities)
    }

    @Test
    fun discoverShows() {
        doAnswer { invocationOnMock ->
            (invocationOnMock.arguments[0] as RemoteDataSource.LoadShowsCallback).onShowsReceived(
                showResponses
            )
            null
        }.`when`(remote).discoverShows(any())

        val showEntities = LiveDataTestUtil.getValue(repository.discoverShows())
        verify(remote).discoverShows(any())

        assertNotNull(showEntities)
        assertEquals(showResponses.size, showEntities.size)
    }

    @Test
    fun discoverShowsError() {
        doAnswer { invocationOnMock ->
            (invocationOnMock.arguments[0] as RemoteDataSource.LoadShowsCallback).onFailure()
            null
        }.`when`(remote).discoverShows(any())

        val showEntities = LiveDataTestUtil.getValue(repository.discoverShows())
        verify(remote).discoverShows(any())

        assertNull(showEntities)
    }
}