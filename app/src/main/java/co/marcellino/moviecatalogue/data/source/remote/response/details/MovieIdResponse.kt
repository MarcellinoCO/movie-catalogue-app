package co.marcellino.moviecatalogue.data.source.remote.response.details

import com.google.gson.annotations.SerializedName

data class MovieIdResponse(
    @field:SerializedName("imdb_id")
    val imdbId: String
)