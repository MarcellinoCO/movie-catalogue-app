package co.marcellino.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import co.marcellino.moviecatalogue.data.Movie
import co.marcellino.moviecatalogue.data.Show
import co.marcellino.moviecatalogue.vo.Resource

interface DataSource {
    fun discoverMovies(): LiveData<Resource<List<Movie>>>
    fun discoverShows(): LiveData<Resource<List<Show>>>

    fun getFavoriteMovies(): LiveData<List<Movie>>
    fun getFavoriteShows(): LiveData<List<Show>>

    fun setMovieFavorite(movie: Movie, isFavorite: Boolean)
    fun setShowFavorite(show: Show, isFavorite: Boolean)
}