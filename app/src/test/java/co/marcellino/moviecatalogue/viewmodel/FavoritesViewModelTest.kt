package co.marcellino.moviecatalogue.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DataSource
import co.marcellino.moviecatalogue.data.Movie
import co.marcellino.moviecatalogue.data.Show
import co.marcellino.moviecatalogue.data.source.FakeRepository
import co.marcellino.moviecatalogue.data.source.local.LocalDataSource
import co.marcellino.moviecatalogue.data.source.remote.RemoteDataSource
import co.marcellino.moviecatalogue.data.source.remote.response.movie.MovieDetailsResponse
import co.marcellino.moviecatalogue.data.source.remote.response.show.ShowDetailsResponse
import co.marcellino.moviecatalogue.utils.AppExecutors
import co.marcellino.moviecatalogue.utils.PagedListUtil
import co.marcellino.moviecatalogue.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class FavoritesViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val local = Mockito.mock(LocalDataSource::class.java)
    private val appExecutors = Mockito.mock(AppExecutors::class.java)

    private val repository = FakeRepository(remote, local, appExecutors)

    private val movieResponses = listOf(MovieDetailsResponse())
    private val showResponses = listOf(ShowDetailsResponse())

    @Test
    fun getFavoriteMovies() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, Movie>

        Mockito.`when`(local.getFavoriteMovies()).thenReturn(dataSourceFactory)
        repository.getFavoriteMovies()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(movieResponses))
        verify(local).getFavoriteMovies()

        Assert.assertNotNull(movieEntities.data)
        Assert.assertEquals(movieResponses.size, movieEntities.data?.size)
    }

    @Test
    fun getFavoriteShows() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, Show>

        Mockito.`when`(local.getFavoriteShows()).thenReturn(dataSourceFactory)
        repository.getFavoriteShows()

        val showEntities = Resource.success(PagedListUtil.mockPagedList(showResponses))
        verify(local).getFavoriteShows()

        Assert.assertNotNull(showEntities.data)
        Assert.assertEquals(showResponses.size, showEntities.data?.size)
    }
}