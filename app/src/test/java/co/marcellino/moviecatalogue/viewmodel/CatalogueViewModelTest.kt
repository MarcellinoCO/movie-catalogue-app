package co.marcellino.moviecatalogue.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import co.marcellino.moviecatalogue.data.Movie
import co.marcellino.moviecatalogue.data.Show
import co.marcellino.moviecatalogue.data.source.Repository
import co.marcellino.moviecatalogue.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CatalogueViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CatalogueViewModel

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var movieObserver: Observer<Resource<List<Movie>>>

    @Mock
    private lateinit var showObserver: Observer<Resource<List<Show>>>

    @Before
    fun setUp() {
        viewModel = CatalogueViewModel(repository)
    }

    @Test
    fun loadMoviesList() {
        val dummyMovies = Resource.success(List(1) { Movie() })
        val movies = MutableLiveData<Resource<List<Movie>>>()
        movies.value = dummyMovies

        `when`(repository.discoverMovies()).thenReturn(movies)

        val movieEntities = viewModel.loadMoviesList().value?.data
        verify(repository).discoverMovies()

        assertNotNull(movieEntities)
        assertEquals(1, movieEntities?.size)

        viewModel.moviesList.observeForever(movieObserver)
        verify(movieObserver).onChanged(dummyMovies)
    }

    @Test
    fun loadShowsList() {
        val dummyShows = Resource.success(List(1) { Show() })
        val shows = MutableLiveData<Resource<List<Show>>>()
        shows.value = dummyShows

        `when`(repository.discoverShows()).thenReturn(shows)

        val showEntities = viewModel.loadShowsList().value?.data
        verify(repository).discoverShows()

        assertNotNull(showEntities)
        assertEquals(1, showEntities?.size)

        viewModel.showsList.observeForever(showObserver)
        verify(showObserver).onChanged(dummyShows)
    }
}
