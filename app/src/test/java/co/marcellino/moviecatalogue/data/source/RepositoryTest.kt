package co.marcellino.moviecatalogue.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DataSource
import co.marcellino.moviecatalogue.data.Movie
import co.marcellino.moviecatalogue.data.Show
import co.marcellino.moviecatalogue.data.source.local.LocalDataSource
import co.marcellino.moviecatalogue.data.source.remote.RemoteDataSource
import co.marcellino.moviecatalogue.data.source.remote.response.movie.MovieDetailsResponse
import co.marcellino.moviecatalogue.data.source.remote.response.show.ShowDetailsResponse
import co.marcellino.moviecatalogue.utils.AppExecutors
import co.marcellino.moviecatalogue.utils.PagedListUtil
import co.marcellino.moviecatalogue.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class RepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val local = Mockito.mock(LocalDataSource::class.java)
    private val appExecutors = Mockito.mock(AppExecutors::class.java)

    private val repository = FakeRepository(remote, local, appExecutors)

    private val movieResponses = listOf(MovieDetailsResponse())
    private val showResponses = listOf(ShowDetailsResponse())

    @Test
    fun discoverMovies() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, Movie>

        `when`(local.getMovies()).thenReturn(dataSourceFactory)
        repository.discoverMovies()

        /** doAnswer { invocationOnMock ->
        (invocationOnMock.arguments[0] as RemoteDataSource.LoadMoviesCallback).onMoviesReceived(
        movieResponses
        )
        null
        }.`when`(remote).discoverMovies(any()) */

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(movieResponses))
        verify(local).getMovies()

        assertNotNull(movieEntities.data)
        assertEquals(movieResponses.size, movieEntities.data?.size)
    }

    /** @Test
    fun discoverMoviesError() {
    doAnswer { invocationOnMock ->
    (invocationOnMock.arguments[0] as RemoteDataSource.LoadMoviesCallback).onFailure()
    null
    }.`when`(remote).discoverMovies(any())

    val movieEntities = LiveDataTestUtil.getValue(repository.discoverMovies())
    verify(remote).discoverMovies(any())

    assertNull(movieEntities.data)
    } */

    @Test
    fun discoverShows() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, Show>

        `when`(local.getShows()).thenReturn(dataSourceFactory)
        repository.discoverShows()

        /** doAnswer { invocationOnMock ->
        (invocationOnMock.arguments[0] as RemoteDataSource.LoadShowsCallback).onShowsReceived(
        showResponses
        )
        null
        }.`when`(remote).discoverShows(any()) */

        val showEntities = Resource.success(PagedListUtil.mockPagedList(showResponses))
        verify(local).getShows()

        assertNotNull(showEntities.data)
        assertEquals(showResponses.size, showEntities.data?.size)
    }

    /** @Test
    fun discoverShowsError() {
    doAnswer { invocationOnMock ->
    (invocationOnMock.arguments[0] as RemoteDataSource.LoadShowsCallback).onFailure()
    null
    }.`when`(remote).discoverShows(any())

    val showEntities = LiveDataTestUtil.getValue(repository.discoverShows())
    verify(remote).discoverShows(any())

    assertNull(showEntities)
    } */
}