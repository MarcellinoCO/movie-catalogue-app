package co.marcellino.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import co.marcellino.moviecatalogue.data.Movie
import co.marcellino.moviecatalogue.data.NetworkBoundResource
import co.marcellino.moviecatalogue.data.Show
import co.marcellino.moviecatalogue.data.source.local.LocalDataSource
import co.marcellino.moviecatalogue.data.source.remote.ApiResponse
import co.marcellino.moviecatalogue.data.source.remote.RemoteDataSource
import co.marcellino.moviecatalogue.data.source.remote.response.movie.MovieDetailsResponse
import co.marcellino.moviecatalogue.data.source.remote.response.show.ShowDetailsResponse
import co.marcellino.moviecatalogue.utils.AppExecutors
import co.marcellino.moviecatalogue.vo.Resource

class Repository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : DataSource {

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(remoteDataSource, localDataSource, appExecutors)
            }
    }

    override fun discoverMovies(): LiveData<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieDetailsResponse>>(appExecutors) {

            override fun loadFromDb(): LiveData<List<Movie>> = localDataSource.getMovies()

            override fun shouldFetch(data: List<Movie>?): Boolean = data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<MovieDetailsResponse>>> =
                remoteDataSource.discoverMovies2()

            override fun saveCallResult(data: List<MovieDetailsResponse>) {
                val moviesList = ArrayList<Movie>()

                for (response in data.toList()) {
                    val movie = Movie(
                        response.title, response.year, response.posterPath,
                        if (response.rating.isNotEmpty()) response.rating[0].rating else "N/A",
                        response.runtime, response.genre, response.plot,
                        response.director, response.writer, response.actors,
                        response.awards,
                        false
                    )
                    moviesList.add(movie)
                }

                localDataSource.insertMovies(moviesList)
            }
        }.asLiveData()

    override fun discoverShows(): LiveData<Resource<List<Show>>> =
        object : NetworkBoundResource<List<Show>, List<ShowDetailsResponse>>(appExecutors) {
            override fun loadFromDb(): LiveData<List<Show>> = localDataSource.getShows()

            override fun shouldFetch(data: List<Show>?): Boolean = data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<ShowDetailsResponse>>> =
                remoteDataSource.discoverShows2()

            override fun saveCallResult(data: List<ShowDetailsResponse>) {
                val showsList = ArrayList<Show>()

                for (response in data.toList()) {
                    val show = Show(
                        response.title, response.year, response.posterPath,
                        if (response.rating.isNotEmpty()) response.rating[0].rating else "N/A",
                        response.runtime, response.genre, response.plot,
                        response.writer, response.actors,
                        response.awards,
                        false
                    )
                    showsList.add(show)
                }

                localDataSource.insertShows(showsList)
            }
        }.asLiveData()

    override fun getFavoriteMovies(): LiveData<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieDetailsResponse>>(appExecutors) {
            override fun loadFromDb(): LiveData<List<Movie>> = localDataSource.getFavoriteMovies()

            override fun shouldFetch(data: List<Movie>?): Boolean = false

            override fun createCall(): LiveData<ApiResponse<List<MovieDetailsResponse>>> =
                remoteDataSource.discoverMovies2()

            override fun saveCallResult(data: List<MovieDetailsResponse>) {}
        }.asLiveData()


    override fun getFavoriteShows(): LiveData<Resource<List<Show>>> =
        object : NetworkBoundResource<List<Show>, List<ShowDetailsResponse>>(appExecutors) {
            override fun loadFromDb(): LiveData<List<Show>> = localDataSource.getFavoriteShows()

            override fun shouldFetch(data: List<Show>?): Boolean = false

            override fun createCall(): LiveData<ApiResponse<List<ShowDetailsResponse>>> =
                remoteDataSource.discoverShows2()

            override fun saveCallResult(data: List<ShowDetailsResponse>) {}
        }.asLiveData()

    override fun setMovieFavorite(movie: Movie, isFavorite: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setMovieFavorite(movie, isFavorite) }
    }

    override fun setShowFavorite(show: Show, isFavorite: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setShowFavorite(show, isFavorite) }
    }
}