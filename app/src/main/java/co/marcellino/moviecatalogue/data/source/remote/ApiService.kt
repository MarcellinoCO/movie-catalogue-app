package co.marcellino.moviecatalogue.data.source.remote

import co.marcellino.moviecatalogue.BuildConfig
import co.marcellino.moviecatalogue.data.source.remote.response.movie.DiscoverMovieResponse
import co.marcellino.moviecatalogue.data.source.remote.response.movie.MovieDetailsResponse
import co.marcellino.moviecatalogue.data.source.remote.response.movie.MovieIdResponse
import co.marcellino.moviecatalogue.data.source.remote.response.show.DiscoverShowResponse
import co.marcellino.moviecatalogue.data.source.remote.response.show.ShowDetailsResponse
import co.marcellino.moviecatalogue.data.source.remote.response.show.ShowSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    companion object {
        val URL_OMDB: String
            get() = "https://www.omdbapi.com"
        val URL_TMDB: String
            get() = "https://api.themoviedb.org/3/"

        val API_KEY_OMDB: String
            get() = BuildConfig.API_KEY_OMDB
        val API_KEY_TMDB: String
            get() = BuildConfig.API_KEY_TMDB
    }

    @GET("discover/movie")
    fun getDiscoverMovies(@Query("api_key") apiKey: String): Call<DiscoverMovieResponse>

    @GET("movie/{movieId}")
    fun getMovieImdbId(
        @Path("movieId") movieId: String,
        @Query("api_key") apiKey: String
    ): Call<MovieIdResponse>

    @GET("/")
    fun getMovieDetails(
        @Query("apikey") apiKey: String,
        @Query("i") imdbId: String
    ): Call<MovieDetailsResponse>

    @GET("discover/tv")
    fun getDiscoverShows(@Query("api_key") apiKey: String): Call<DiscoverShowResponse>

    @GET("/?type=series")
    fun getShowSearch(
        @Query("apikey") apiKey: String,
        @Query("s") name: String
    ): Call<ShowSearchResponse>

    @GET("/")
    fun getShowDetails(
        @Query("apikey") apiKey: String,
        @Query("i") imdbId: String
    ): Call<ShowDetailsResponse>
}