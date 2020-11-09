package co.marcellino.moviecatalogue.viewmodel

import co.marcellino.moviecatalogue.model.Movie
import co.marcellino.moviecatalogue.model.Show
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DetailsViewModelTest {

    private lateinit var viewModel: DetailsViewModel

    @Before
    fun setUp() {
        viewModel = DetailsViewModel()
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
}