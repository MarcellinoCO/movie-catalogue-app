package co.marcellino.moviecatalogue.data.source.remote.response.movie

import com.google.gson.annotations.SerializedName

data class MovieIdResponse(
    @field:SerializedName("imdb_id")
    val imdbId: String = ""
)