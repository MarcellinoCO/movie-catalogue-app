package co.marcellino.moviecatalogue.viewmodel

import co.marcellino.moviecatalogue.data.Movie
import co.marcellino.moviecatalogue.data.Show
import co.marcellino.moviecatalogue.data.source.Repository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailsViewModelTest {

    private lateinit var viewModel: DetailsViewModel

    @Mock
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        viewModel = DetailsViewModel(repository)
    }

    @Test
    fun isTypeInitialized() {
        assertEquals(DetailsViewModel.TYPE_UNINITIALIZED, viewModel.type)
        assertEquals(false, viewModel.isTypeInitialized())

        viewModel.type = DetailsViewModel.TYPE_MOVIE
        assertEquals(DetailsViewModel.TYPE_MOVIE, viewModel.type)

        viewModel.type = DetailsViewModel.TYPE_SHOW
        assertEquals(DetailsViewModel.TYPE_SHOW, viewModel.type)
    }

    @Test
    fun isMovieInitialized() {
        assertEquals(false, viewModel.isMovieInitialized())

        viewModel.movie = Movie()
        assert(viewModel.isMovieInitialized())
        assertEquals(Movie(), viewModel.movie)
    }

    @Test
    fun isShowInitialized() {
        assertEquals(false, viewModel.isShowInitialized())

        viewModel.show = Show()
        assert(viewModel.isShowInitialized())
        assertEquals(Show(), viewModel.show)
    }

    @Test
    fun toggleMovieFavorite() {
        viewModel.type = DetailsViewModel.TYPE_MOVIE

        viewModel.movie = Movie()
        assertEquals(false, viewModel.movie.isFavorite)

        viewModel.toggleFavorite()
        assertEquals(true, viewModel.movie.isFavorite)
    }

    @Test
    fun toggleShowFavorite() {
        viewModel.type = DetailsViewModel.TYPE_SHOW

        viewModel.show = Show()
        assertEquals(false, viewModel.show.isFavorite)

        viewModel.toggleFavorite()
        assertEquals(true, viewModel.show.isFavorite)
    }
}