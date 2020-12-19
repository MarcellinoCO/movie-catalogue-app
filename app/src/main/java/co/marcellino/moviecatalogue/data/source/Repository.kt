package co.marcellino.moviecatalogue.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.marcellino.moviecatalogue.data.Movie
import co.marcellino.moviecatalogue.data.Show
import co.marcellino.moviecatalogue.data.source.remote.RemoteDataSource
import co.marcellino.moviecatalogue.data.source.remote.response.movie.MovieDetailsResponse
import co.marcellino.moviecatalogue.data.source.remote.response.show.ShowDetailsResponse

class Repository private constructor(private val remoteDataSource: RemoteDataSource) : DataSource {

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(remoteDataSource: RemoteDataSource): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(remoteDataSource)
            }
    }

    override fun discoverMovies(): LiveData<List<Movie>> {
        val movieResults = MutableLiveData<List<Movie>>()
        remoteDataSource.discoverMovies(object : RemoteDataSource.LoadMoviesCallback {
            override fun onMoviesReceived(movieDetailsResponses: List<MovieDetailsResponse>) {
                val movieList = ArrayList<Movie>()
                for (movieResponse in movieDetailsResponses) {
                    val movie = Movie(
                        title = movieResponse.title,
                        year = movieResponse.year,
                        posterPath = movieResponse.posterPath,
                        rating = if (movieResponse.rating.isNotEmpty()) movieResponse.rating[0].rating else "N/A",
                        runtime = movieResponse.runtime,
                        genre = movieResponse.genre,
                        plot = movieResponse.plot,
                        director = movieResponse.director,
                        writer = movieResponse.writer,
                        actors = movieResponse.actors,
                        awards = movieResponse.awards
                    )
                    movieList.add(movie)
                }
                Log.d("HAI", movieList.toString())
                movieResults.postValue(movieList)
            }

            override fun onFailure() {
                Log.e("Repository Error", "")
            }
        })

        return movieResults
    }

    override fun discoverShows(): LiveData<List<Show>> {
        val showResults = MutableLiveData<List<Show>>()
        remoteDataSource.discoverShows(object : RemoteDataSource.LoadShowsCallback {
            override fun onShowsReceived(showDetailsResponses: List<ShowDetailsResponse>) {
                val showList = ArrayList<Show>()
                for (showResponse in showDetailsResponses) {
                    val show = Show(
                        title = showResponse.title,
                        year = showResponse.year,
                        posterPath = showResponse.posterPath,
                        rating = if (showResponse.rating.isNotEmpty()) showResponse.rating[0].rating else "N/A",
                        runtime = showResponse.runtime,
                        genre = showResponse.genre,
                        plot = showResponse.plot,
                        writer = showResponse.writer,
                        actors = showResponse.actors,
                        awards = showResponse.awards
                    )
                    showList.add(show)
                }
                showResults.postValue(showList)
            }

            override fun onFailure() {}
        })

        return showResults
    }
}