package co.marcellino.moviecatalogue.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.marcellino.moviecatalogue.data.source.remote.response.movie.DiscoverMovieResponse
import co.marcellino.moviecatalogue.data.source.remote.response.movie.MovieDetailsResponse
import co.marcellino.moviecatalogue.data.source.remote.response.movie.MovieIdResponse
import co.marcellino.moviecatalogue.data.source.remote.response.show.DiscoverShowResponse
import co.marcellino.moviecatalogue.data.source.remote.response.show.ShowDetailsResponse
import co.marcellino.moviecatalogue.data.source.remote.response.show.ShowSearchResponse
import co.marcellino.moviecatalogue.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor() {

    companion object {
        val TAG = RemoteDataSource::class.java.simpleName

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource = instance ?: synchronized(this) {
            instance ?: RemoteDataSource()
        }
    }

    fun discoverMovies(callback: LoadMoviesCallback) {
        EspressoIdlingResource.increment()

        val discoverMovieClient =
            ApiConfig.getApiService(ApiService.URL_TMDB).getDiscoverMovies(ApiService.API_KEY_TMDB)
        discoverMovieClient.enqueue(object : Callback<DiscoverMovieResponse> {
            override fun onResponse(
                call: Call<DiscoverMovieResponse>,
                response: Response<DiscoverMovieResponse>
            ) {
                if (!response.isSuccessful) {
                    Log.e(TAG, response.message())
                    callback.onFailure()

                    EspressoIdlingResource.decrement()
                    return
                }

                val movieResponses = ArrayList<MovieDetailsResponse>()
                val movieSummaryResponses = response.body()?.movieResults

                if (movieSummaryResponses == null) {
                    callback.onMoviesReceived(movieResponses)

                    EspressoIdlingResource.decrement()
                    return
                }

                for ((index, movieSummary) in movieSummaryResponses.withIndex()) {
                    val movieIdClient = ApiConfig.getApiService(ApiService.URL_TMDB)
                        .getMovieImdbId(movieSummary.id, ApiService.API_KEY_TMDB)
                    movieIdClient.enqueue(object : Callback<MovieIdResponse> {
                        override fun onResponse(
                            call: Call<MovieIdResponse>,
                            response: Response<MovieIdResponse>
                        ) {
                            if (!response.isSuccessful) {
                                Log.e(TAG, response.message())
                                callback.onFailure()

                                EspressoIdlingResource.decrement()
                                return
                            }

                            val movieImdbIdResponse = response.body()?.imdbId ?: ""
                            val movieDetailsClient =
                                ApiConfig.getApiService(ApiService.URL_OMDB)
                                    .getMovieDetails(ApiService.API_KEY_OMDB, movieImdbIdResponse)
                            movieDetailsClient.enqueue(object : Callback<MovieDetailsResponse> {
                                override fun onResponse(
                                    call: Call<MovieDetailsResponse>,
                                    response: Response<MovieDetailsResponse>
                                ) {
                                    if (!response.isSuccessful) {
                                        Log.e(TAG, response.message())
                                        callback.onFailure()

                                        EspressoIdlingResource.decrement()
                                        return
                                    }

                                    val movieDetailsResponse =
                                        response.body() as MovieDetailsResponse
                                    movieResponses.add(movieDetailsResponse)

                                    if (index == movieSummaryResponses.size - 1) {
                                        callback.onMoviesReceived(movieResponses)
                                        EspressoIdlingResource.decrement()
                                    }
                                }

                                override fun onFailure(
                                    call: Call<MovieDetailsResponse>,
                                    t: Throwable
                                ) {
                                    Log.e(TAG, t.message.toString())
                                    callback.onFailure()
                                    EspressoIdlingResource.decrement()
                                }
                            })
                        }

                        override fun onFailure(call: Call<MovieIdResponse>, t: Throwable) {
                            Log.e(TAG, t.message.toString())
                            callback.onFailure()
                            EspressoIdlingResource.decrement()
                        }
                    })
                }
            }

            override fun onFailure(call: Call<DiscoverMovieResponse>, t: Throwable) {
                Log.e(TAG, t.message.toString())
                callback.onFailure()
                EspressoIdlingResource.decrement()
            }
        })
    }

    fun discoverMovies2(): LiveData<ApiResponse<List<MovieDetailsResponse>>> {
        EspressoIdlingResource.increment()

        val resultMovies = MutableLiveData<ApiResponse<List<MovieDetailsResponse>>>()
        val discoverMovieClient =
            ApiConfig.getApiService(ApiService.URL_TMDB).getDiscoverMovies(ApiService.API_KEY_TMDB)
        discoverMovieClient.enqueue(object : Callback<DiscoverMovieResponse> {
            override fun onResponse(
                call: Call<DiscoverMovieResponse>,
                response: Response<DiscoverMovieResponse>
            ) {
                if (!response.isSuccessful) {
                    Log.e(TAG, response.message())
                    resultMovies.value = ApiResponse.error(listOf())

                    EspressoIdlingResource.decrement()
                    return
                }

                val movieResponses = ArrayList<MovieDetailsResponse>()
                val movieSummaryResponses = response.body()?.movieResults

                if (movieSummaryResponses == null) {
                    resultMovies.value = ApiResponse.error(listOf())

                    EspressoIdlingResource.decrement()
                    return
                }

                for ((index, movieSummary) in movieSummaryResponses.withIndex()) {
                    val movieIdClient = ApiConfig.getApiService(ApiService.URL_TMDB)
                        .getMovieImdbId(movieSummary.id, ApiService.API_KEY_TMDB)
                    movieIdClient.enqueue(object : Callback<MovieIdResponse> {
                        override fun onResponse(
                            call: Call<MovieIdResponse>,
                            response: Response<MovieIdResponse>
                        ) {
                            if (!response.isSuccessful) {
                                Log.e(TAG, response.message())
                                resultMovies.value = ApiResponse.error(listOf())

                                EspressoIdlingResource.decrement()
                                return
                            }

                            val movieImdbIdResponse = response.body()?.imdbId ?: ""
                            val movieDetailsClient =
                                ApiConfig.getApiService(ApiService.URL_OMDB)
                                    .getMovieDetails(ApiService.API_KEY_OMDB, movieImdbIdResponse)
                            movieDetailsClient.enqueue(object : Callback<MovieDetailsResponse> {
                                override fun onResponse(
                                    call: Call<MovieDetailsResponse>,
                                    response: Response<MovieDetailsResponse>
                                ) {
                                    if (!response.isSuccessful) {
                                        Log.e(TAG, response.message())
                                        resultMovies.value = ApiResponse.error(listOf())

                                        EspressoIdlingResource.decrement()
                                        return
                                    }

                                    val movieDetailsResponse =
                                        response.body() as MovieDetailsResponse
                                    movieResponses.add(movieDetailsResponse)

                                    if (index == movieSummaryResponses.size - 1) {
                                        resultMovies.value = ApiResponse.success(movieResponses)
                                        EspressoIdlingResource.decrement()
                                    }
                                }

                                override fun onFailure(
                                    call: Call<MovieDetailsResponse>,
                                    t: Throwable
                                ) {
                                    Log.e(TAG, t.message.toString())
                                    resultMovies.value = ApiResponse.error(listOf())
                                    EspressoIdlingResource.decrement()
                                }
                            })
                        }

                        override fun onFailure(call: Call<MovieIdResponse>, t: Throwable) {
                            Log.e(TAG, t.message.toString())
                            resultMovies.value = ApiResponse.error(listOf())
                            EspressoIdlingResource.decrement()
                        }
                    })
                }
            }

            override fun onFailure(call: Call<DiscoverMovieResponse>, t: Throwable) {
                Log.e(TAG, t.message.toString())
                resultMovies.value = ApiResponse.error(listOf())
                EspressoIdlingResource.decrement()
            }
        })

        return resultMovies
    }

    fun discoverShows(callback: LoadShowsCallback) {
        EspressoIdlingResource.increment()

        val discoverShowClient =
            ApiConfig.getApiService(ApiService.URL_TMDB).getDiscoverShows(ApiService.API_KEY_TMDB)
        discoverShowClient.enqueue(object : Callback<DiscoverShowResponse> {
            override fun onResponse(
                call: Call<DiscoverShowResponse>,
                response: Response<DiscoverShowResponse>
            ) {
                if (!response.isSuccessful) {
                    Log.e(TAG, response.message())
                    callback.onFailure()

                    EspressoIdlingResource.decrement()
                    return
                }

                val showResponses = ArrayList<ShowDetailsResponse>()
                val showNameResponses = response.body()?.showNames

                if (showNameResponses == null) {
                    callback.onShowsReceived(showResponses)

                    EspressoIdlingResource.decrement()
                    return
                }

                for ((index, showNameResponse) in showNameResponses.withIndex()) {
                    val showSearchClient = ApiConfig.getApiService(ApiService.URL_OMDB)
                        .getShowSearch(ApiService.API_KEY_OMDB, showNameResponse.name)
                    showSearchClient.enqueue(object : Callback<ShowSearchResponse> {
                        override fun onResponse(
                            call: Call<ShowSearchResponse>,
                            response: Response<ShowSearchResponse>
                        ) {
                            if (!response.isSuccessful) {
                                Log.e(TAG, response.message())
                                callback.onFailure()

                                EspressoIdlingResource.decrement()
                                return
                            }

                            val showImdbIdResponse =
                                response.body()?.showSearchResults?.get(0)?.imdbId ?: ""
                            val showDetailsClient = ApiConfig.getApiService(ApiService.URL_OMDB)
                                .getShowDetails(ApiService.API_KEY_OMDB, showImdbIdResponse)
                            showDetailsClient.enqueue(object : Callback<ShowDetailsResponse> {
                                override fun onResponse(
                                    call: Call<ShowDetailsResponse>,
                                    response: Response<ShowDetailsResponse>
                                ) {
                                    if (!response.isSuccessful) {
                                        Log.e(TAG, response.message())
                                        callback.onFailure()

                                        EspressoIdlingResource.decrement()
                                        return
                                    }

                                    val showDetailsResponse = response.body() as ShowDetailsResponse
                                    showResponses.add(showDetailsResponse)

                                    if (index == showNameResponses.size - 1) {
                                        callback.onShowsReceived(showResponses)
                                        EspressoIdlingResource.decrement()
                                    }
                                }

                                override fun onFailure(
                                    call: Call<ShowDetailsResponse>,
                                    t: Throwable
                                ) {
                                    Log.e(TAG, t.message.toString())
                                    callback.onFailure()
                                    EspressoIdlingResource.decrement()
                                }
                            })
                        }

                        override fun onFailure(call: Call<ShowSearchResponse>, t: Throwable) {
                            Log.e(TAG, t.message.toString())
                            callback.onFailure()
                            EspressoIdlingResource.decrement()
                        }
                    })
                }
            }

            override fun onFailure(call: Call<DiscoverShowResponse>, t: Throwable) {
                Log.e(TAG, t.message.toString())
                callback.onFailure()
                EspressoIdlingResource.decrement()
            }
        })
    }

    fun discoverShows2(): LiveData<ApiResponse<List<ShowDetailsResponse>>> {
        EspressoIdlingResource.increment()

        val resultShows = MutableLiveData<ApiResponse<List<ShowDetailsResponse>>>()
        val discoverShowClient =
            ApiConfig.getApiService(ApiService.URL_TMDB).getDiscoverShows(ApiService.API_KEY_TMDB)
        discoverShowClient.enqueue(object : Callback<DiscoverShowResponse> {
            override fun onResponse(
                call: Call<DiscoverShowResponse>,
                response: Response<DiscoverShowResponse>
            ) {
                if (!response.isSuccessful) {
                    Log.e(TAG, response.message())
                    resultShows.value = ApiResponse.error(listOf())

                    EspressoIdlingResource.decrement()
                    return
                }

                val showResponses = ArrayList<ShowDetailsResponse>()
                val showNameResponses = response.body()?.showNames

                if (showNameResponses == null) {
                    resultShows.value = ApiResponse.error(listOf())

                    EspressoIdlingResource.decrement()
                    return
                }

                for ((index, showNameResponse) in showNameResponses.withIndex()) {
                    val showSearchClient = ApiConfig.getApiService(ApiService.URL_OMDB)
                        .getShowSearch(ApiService.API_KEY_OMDB, showNameResponse.name)
                    showSearchClient.enqueue(object : Callback<ShowSearchResponse> {
                        override fun onResponse(
                            call: Call<ShowSearchResponse>,
                            response: Response<ShowSearchResponse>
                        ) {
                            if (!response.isSuccessful) {
                                Log.e(TAG, response.message())
                                resultShows.value = ApiResponse.error(listOf())

                                EspressoIdlingResource.decrement()
                                return
                            }

                            val showImdbIdResponse =
                                response.body()?.showSearchResults?.get(0)?.imdbId ?: ""
                            val showDetailsClient = ApiConfig.getApiService(ApiService.URL_OMDB)
                                .getShowDetails(ApiService.API_KEY_OMDB, showImdbIdResponse)
                            showDetailsClient.enqueue(object : Callback<ShowDetailsResponse> {
                                override fun onResponse(
                                    call: Call<ShowDetailsResponse>,
                                    response: Response<ShowDetailsResponse>
                                ) {
                                    if (!response.isSuccessful) {
                                        Log.e(TAG, response.message())
                                        resultShows.value = ApiResponse.error(listOf())

                                        EspressoIdlingResource.decrement()
                                        return
                                    }

                                    val showDetailsResponse = response.body() as ShowDetailsResponse
                                    showResponses.add(showDetailsResponse)

                                    if (index == showNameResponses.size - 1) {
                                        resultShows.value = ApiResponse.success(showResponses)
                                        EspressoIdlingResource.decrement()
                                    }
                                }

                                override fun onFailure(
                                    call: Call<ShowDetailsResponse>,
                                    t: Throwable
                                ) {
                                    Log.e(TAG, t.message.toString())
                                    resultShows.value = ApiResponse.error(listOf())
                                    EspressoIdlingResource.decrement()
                                }
                            })
                        }

                        override fun onFailure(call: Call<ShowSearchResponse>, t: Throwable) {
                            Log.e(TAG, t.message.toString())
                            resultShows.value = ApiResponse.error(listOf())
                            EspressoIdlingResource.decrement()
                        }
                    })
                }
            }

            override fun onFailure(call: Call<DiscoverShowResponse>, t: Throwable) {
                Log.e(TAG, t.message.toString())
                resultShows.value = ApiResponse.error(listOf())
                EspressoIdlingResource.decrement()
            }
        })

        return resultShows
    }

    interface LoadMoviesCallback {
        fun onMoviesReceived(movieDetailsResponses: List<MovieDetailsResponse>)
        fun onFailure()
    }

    interface LoadShowsCallback {
        fun onShowsReceived(showDetailsResponses: List<ShowDetailsResponse>)
        fun onFailure()
    }
}