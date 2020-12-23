package co.marcellino.moviecatalogue.data.source.local

import androidx.lifecycle.LiveData
import co.marcellino.moviecatalogue.data.Movie
import co.marcellino.moviecatalogue.data.Show
import co.marcellino.moviecatalogue.data.source.local.room.CatalogueDao

class LocalDataSource private constructor(private val catalogueDao: CatalogueDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(catalogueDao: CatalogueDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(catalogueDao)
    }

    fun getMovies(): LiveData<List<Movie>> = catalogueDao.getMovies()

    fun getFavoriteMovies(): LiveData<List<Movie>> = catalogueDao.getFavoriteMovies()

    fun getShows(): LiveData<List<Show>> = catalogueDao.getShows()

    fun getFavoriteShows(): LiveData<List<Show>> = catalogueDao.getFavoriteShows()

    fun insertMovies(movies: List<Movie>) = catalogueDao.insertMovies(movies)

    fun insertShows(shows: List<Show>) = catalogueDao.insertShows(shows)

    fun deleteMovie(movie: Movie) = catalogueDao.deleteMovie(movie)

    fun deleteShow(show: Show) = catalogueDao.deleteShow(show)

    fun setMovieFavorite(movie: Movie, isFavorite: Boolean) {
        movie.isFavorite = isFavorite
        catalogueDao.updateMovie(movie)
    }

    fun setShowFavorite(show: Show, isFavorite: Boolean) {
        show.isFavorite = isFavorite
        catalogueDao.updateShow(show)
    }
}