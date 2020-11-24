package co.marcellino.moviecatalogue.data.source.remote.response.movie

import com.google.gson.annotations.SerializedName

data class MovieRatingsResponse(
    @field:SerializedName("Value")
    val rating: String = ""
)