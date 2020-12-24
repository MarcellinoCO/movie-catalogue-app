package co.marcellino.moviecatalogue.data.source.local.room

import androidx.paging.DataSource
import androidx.room.*
import co.marcellino.moviecatalogue.data.Movie
import co.marcellino.moviecatalogue.data.Show

@Dao
interface CatalogueDao {

//    @Query("SELECT * FROM movie")
//    fun getMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie")
    fun getMovies(): DataSource.Factory<Int, Movie>

//    @Query("SELECT * FROM movie WHERE isFavorite = 1")
//    fun getFavoriteMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie WHERE isFavorite = 1")
    fun getFavoriteMovies(): DataSource.Factory<Int, Movie>

//    @Query("SELECT * FROM show")
//    fun getShows(): LiveData<List<Show>>

    @Query("SELECT * FROM show")
    fun getShows(): DataSource.Factory<Int, Show>

//    @Query("SELECT * FROM show WHERE isFavorite = 1")
//    fun getFavoriteShows(): LiveData<List<Show>>

    @Query("SELECT * FROM show WHERE isFavorite = 1")
    fun getFavoriteShows(): DataSource.Factory<Int, Show>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShows(shows: List<Show>)

    @Update
    fun updateMovie(movie: Movie)

    @Update
    fun updateShow(show: Show)

    @Delete
    fun deleteMovie(movie: Movie)

    @Delete
    fun deleteShow(show: Show)
}