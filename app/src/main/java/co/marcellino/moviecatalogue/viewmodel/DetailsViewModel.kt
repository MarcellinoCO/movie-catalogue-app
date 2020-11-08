package co.marcellino.moviecatalogue.viewmodel

import androidx.lifecycle.ViewModel
import co.marcellino.moviecatalogue.model.Movie
import co.marcellino.moviecatalogue.model.Show

class DetailsViewModel : ViewModel() {

    companion object {
        const val TYPE_UNINITIALIZED = -1
        const val TYPE_MOVIE = 0
        const val TYPE_SHOW = 1
    }

    var type = -1
    fun isTypeInitialized(): Boolean = (type != TYPE_UNINITIALIZED)

    lateinit var movie: Movie
    fun isMovieInitialized(): Boolean = this::movie.isInitialized

    lateinit var show: Show
    fun isShowInitialized(): Boolean = this::show.isInitialized
}