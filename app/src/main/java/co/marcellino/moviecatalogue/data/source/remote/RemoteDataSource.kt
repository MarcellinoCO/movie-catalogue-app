package co.marcellino.moviecatalogue.data.source.remote

import android.util.Log
import co.marcellino.moviecatalogue.data.source.remote.response.details.MovieDetailsResponse
import co.marcellino.moviecatalogue.data.source.remote.response.details.MovieIdResponse
import co.marcellino.moviecatalogue.data.source.remote.response.details.ShowDetailsResponse
import co.marcellino.moviecatalogue.data.source.remote.response.details.ShowSearchResponse
import co.marcellino.moviecatalogue.data.source.remote.response.discover.DiscoverMovieResponse
import co.marcellino.moviecatalogue.data.source.remote.response.discover.DiscoverShowResponse
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
                    return
                }

                val movieResponses = ArrayList<MovieDetailsResponse>()
                val movieSummaryResponses = response.body()?.movieResults

                if (movieSummaryResponses == null) {
                    callback.onMoviesReceived(movieResponses)
                    return
                }

                for (movieSummary in movieSummaryResponses) {
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
                                        return
                                    }

                                    val movieDetailsResponse =
                                        response.body() as MovieDetailsResponse
                                    movieResponses.add(movieDetailsResponse)

                                    callback.onMoviesReceived(movieResponses)
                                }

                                override fun onFailure(
                                    call: Call<MovieDetailsResponse>,
                                    t: Throwable
                                ) {
                                    Log.e(TAG, t.message.toString())
                                    callback.onFailure()
                                }
                            })
                        }

                        override fun onFailure(call: Call<MovieIdResponse>, t: Throwable) {
                            Log.e(TAG, t.message.toString())
                            callback.onFailure()
                        }
                    })
                }
            }

            override fun onFailure(call: Call<DiscoverMovieResponse>, t: Throwable) {
                Log.e(TAG, t.message.toString())
                callback.onFailure()
            }
        })
    }

    fun discoverShows(callback: LoadShowsCallback) {
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
                    return
                }

                val showResponses = ArrayList<ShowDetailsResponse>()
                val showNameResponses = response.body()?.showNames

                if (showNameResponses == null) {
                    callback.onShowsReceived(showResponses)
                    return
                }

                for (showNameResponse in showNameResponses) {
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
                                        return
                                    }

                                    val showDetailsResponse = response.body() as ShowDetailsResponse
                                    showResponses.add(showDetailsResponse)

                                    callback.onShowsReceived(showResponses)
                                }

                                override fun onFailure(
                                    call: Call<ShowDetailsResponse>,
                                    t: Throwable
                                ) {
                                    Log.e(TAG, t.message.toString())
                                    callback.onFailure()
                                }
                            })
                        }

                        override fun onFailure(call: Call<ShowSearchResponse>, t: Throwable) {
                            Log.e(TAG, t.message.toString())
                            callback.onFailure()
                        }
                    })
                }
            }

            override fun onFailure(call: Call<DiscoverShowResponse>, t: Throwable) {
                Log.e(TAG, t.message.toString())
                callback.onFailure()
            }
        })
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