package co.marcellino.moviecatalogue.viewmodel

import androidx.lifecycle.ViewModel
import co.marcellino.moviecatalogue.data.Movie
import co.marcellino.moviecatalogue.data.Show
import co.marcellino.moviecatalogue.data.source.Repository

class DetailsViewModel(private val repository: Repository) : ViewModel() {

    companion object {
        const val TYPE_UNINITIALIZED = -1
        const val TYPE_MOVIE = 0
        const val TYPE_SHOW = 1
    }

    var type = TYPE_UNINITIALIZED
    fun isTypeInitialized(): Boolean = (type != TYPE_UNINITIALIZED)

    lateinit var movie: Movie
    fun isMovieInitialized(): Boolean = this::movie.isInitialized

    lateinit var show: Show
    fun isShowInitialized(): Boolean = this::show.isInitialized

    fun toggleFavorite() {
        if (type == TYPE_MOVIE) {
            repository.setMovieFavorite(movie, !movie.isFavorite)
            movie.isFavorite = !movie.isFavorite
        } else if (type == TYPE_SHOW) {
            repository.setShowFavorite(show, !show.isFavorite)
            show.isFavorite = !show.isFavorite
        }
    }
}