package co.marcellino.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
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

class FakeRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : DataSource {

    override fun discoverMovies(): LiveData<Resource<PagedList<Movie>>> =
        object : NetworkBoundResource<PagedList<Movie>, List<MovieDetailsResponse>>(appExecutors) {

            override fun loadFromDb(): LiveData<PagedList<Movie>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<Movie>?): Boolean =
                data == null || data.isEmpty()

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

    override fun discoverShows(): LiveData<Resource<PagedList<Show>>> =
        object : NetworkBoundResource<PagedList<Show>, List<ShowDetailsResponse>>(appExecutors) {
            override fun loadFromDb(): LiveData<PagedList<Show>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getShows(), config).build()
            }

            override fun shouldFetch(data: PagedList<Show>?): Boolean =
                data == null || data.isEmpty()

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

    override fun getFavoriteMovies(): LiveData<Resource<PagedList<Movie>>> =
        object : NetworkBoundResource<PagedList<Movie>, List<MovieDetailsResponse>>(appExecutors) {
            override fun loadFromDb(): LiveData<PagedList<Movie>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getFavoriteMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<Movie>?): Boolean = false

            override fun createCall(): LiveData<ApiResponse<List<MovieDetailsResponse>>> =
                remoteDataSource.discoverMovies2()

            override fun saveCallResult(data: List<MovieDetailsResponse>) {}
        }.asLiveData()


    override fun getFavoriteShows(): LiveData<Resource<PagedList<Show>>> =
        object : NetworkBoundResource<PagedList<Show>, List<ShowDetailsResponse>>(appExecutors) {
            override fun loadFromDb(): LiveData<PagedList<Show>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getFavoriteShows(), config).build()
            }

            override fun shouldFetch(data: PagedList<Show>?): Boolean = false

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