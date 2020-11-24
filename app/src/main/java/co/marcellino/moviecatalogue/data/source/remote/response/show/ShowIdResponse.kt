package co.marcellino.moviecatalogue.data.source.remote.response.show

import com.google.gson.annotations.SerializedName

data class ShowIdResponse(
    @field:SerializedName("imdbID")
    val imdbId: String = ""
)