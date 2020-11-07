package co.marcellino.moviecatalogue.utils

import co.marcellino.moviecatalogue.model.Movie
import co.marcellino.moviecatalogue.model.Show
import org.json.JSONObject

object DataParser {

    private const val KEY_MOVIES = "Movies"
    private const val KEY_SHOWS = "TVShows"

    private const val KEY_TITLE = "Title"
    private const val KEY_YEAR = "Year"

    private const val KEY_POSTER_PATH = "Poster"

    private const val KEY_RATINGS = "Ratings"
    private const val KEY_RATING_SOURCE = 0
    private const val KEY_RATING_VALUE = "Value"

    private const val KEY_RUNTIME = "Runtime"
    private const val KEY_GENRE = "Genre"
    private const val KEY_PLOT = "Plot"

    private const val KEY_DIRECTOR = "Director"
    private const val KEY_WRITER = "Writer"

    private const val KEY_ACTORS = "Actors"
    private const val KEY_AWARDS = "Awards"

    fun getMoviesList(jsonString: String?): ArrayList<Movie> {
        if (jsonString == null) return ArrayList()

        val listMovies = ArrayList<Movie>()

        try {
            val reader = JSONObject(jsonString)

            val moviesArray = reader.getJSONArray(KEY_MOVIES)
            for (index in 0 until moviesArray.length()) {
                val movieObject = moviesArray.getJSONObject(index)

                val ratingsArray = movieObject.getJSONArray(KEY_RATINGS)
                val ratingSourceObject = ratingsArray.getJSONObject(KEY_RATING_SOURCE)

                val movie = Movie(
                    title = movieObject.getString(KEY_TITLE),
                    year = movieObject.getString(KEY_YEAR),

                    posterPath = movieObject.getString(KEY_POSTER_PATH),
                    rating = ratingSourceObject.getString(KEY_RATING_VALUE),

                    runtime = movieObject.getString(KEY_RUNTIME),
                    genre = movieObject.getString(KEY_GENRE),
                    plot = movieObject.getString(KEY_PLOT),

                    director = movieObject.getString(KEY_DIRECTOR),
                    writer = movieObject.getString(KEY_WRITER),
                    actors = movieObject.getString(KEY_ACTORS),
                    awards = movieObject.getString(KEY_AWARDS)
                )
                listMovies.add(movie)
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

        return listMovies
    }

    fun getShowsList(jsonString: String?): ArrayList<Show> {
        if (jsonString == null) return ArrayList()

        val listShows = ArrayList<Show>()

        try {
            val reader = JSONObject(jsonString)

            val showsArray = reader.getJSONArray(KEY_SHOWS)
            for (index in 0 until showsArray.length()) {
                val showObject = showsArray.getJSONObject(index)

                val ratingsArray = showObject.getJSONArray(KEY_RATINGS)
                val ratingSourceObject = ratingsArray.getJSONObject(KEY_RATING_SOURCE)

                val show = Show(
                    title = showObject.getString(KEY_TITLE),
                    year = showObject.getString(KEY_YEAR),

                    posterPath = showObject.getString(KEY_POSTER_PATH),
                    rating = ratingSourceObject.getString(KEY_RATING_VALUE),

                    runtime = showObject.getString(KEY_RUNTIME),
                    genre = showObject.getString(KEY_GENRE),
                    plot = showObject.getString(KEY_PLOT),

                    writer = showObject.getString(KEY_WRITER),
                    actors = showObject.getString(KEY_ACTORS),
                    awards = showObject.getString(KEY_AWARDS)
                )
                listShows.add(show)
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

        return listShows
    }
}