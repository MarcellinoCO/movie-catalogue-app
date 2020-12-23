package co.marcellino.moviecatalogue.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import co.marcellino.moviecatalogue.data.Movie
import co.marcellino.moviecatalogue.data.Show

@Dao
interface CatalogueDao {

    @Query("SELECT * FROM movie WHERE isFavorite = 1")
    fun getFavoriteMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM show WHERE isFavorite = 1")
    fun getFavoriteShows(): LiveData<List<Show>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteMovies(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteShows(shows: List<Show>)

    @Delete
    fun deleteFavoriteMovie(movie: Movie)

    @Delete
    fun deleteFavoriteShow(show: Show)
}