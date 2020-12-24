package co.marcellino.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import co.marcellino.moviecatalogue.data.Movie
import co.marcellino.moviecatalogue.data.Show
import co.marcellino.moviecatalogue.vo.Resource

interface DataSource {
    fun discoverMovies(): LiveData<Resource<PagedList<Movie>>>
    fun discoverShows(): LiveData<Resource<PagedList<Show>>>

    fun getFavoriteMovies(): LiveData<Resource<PagedList<Movie>>>
    fun getFavoriteShows(): LiveData<Resource<PagedList<Show>>>

    fun setMovieFavorite(movie: Movie, isFavorite: Boolean)
    fun setShowFavorite(show: Show, isFavorite: Boolean)
}