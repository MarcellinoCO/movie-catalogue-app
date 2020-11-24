package co.marcellino.moviecatalogue.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import co.marcellino.moviecatalogue.data.Movie
import co.marcellino.moviecatalogue.data.Show
import co.marcellino.moviecatalogue.data.source.Repository
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
    private lateinit var movieObserver: Observer<List<Movie>>

    @Mock
    private lateinit var showObserver: Observer<List<Show>>

    @Before
    fun setUp() {
        viewModel = CatalogueViewModel(repository)
    }

    @Test
    fun loadMoviesList() {
        val dummyMovies = List(1) { _ -> Movie() }
        val movies = MutableLiveData<List<Movie>>()
        movies.value = dummyMovies

        `when`(repository.discoverMovies()).thenReturn(movies)

        val movieEntities = viewModel.loadMoviesList().value
        verify(repository).discoverMovies()

        assertNotNull(movieEntities)
        assertEquals(1, movieEntities?.size)

        viewModel.moviesList.observeForever(movieObserver)
        verify(movieObserver).onChanged(dummyMovies)
    }

    @Test
    fun loadShowsList() {
        val dummyShows = List(1) { _ -> Show() }
        val shows = MutableLiveData<List<Show>>()
        shows.value = dummyShows

        `when`(repository.discoverShows()).thenReturn(shows)

        val showEntities = viewModel.loadShowsList().value
        verify(repository).discoverShows()

        assertNotNull(showEntities)
        assertEquals(1, showEntities?.size)

        viewModel.showsList.observeForever(showObserver)
        verify(showObserver).onChanged(dummyShows)
    }
}
