package co.marcellino.moviecatalogue.data.source.remote.response.details

import com.google.gson.annotations.SerializedName

data class ShowSearchResponse(
    @field:SerializedName("totalResults")
    val totalResults: Int,

    @field:SerializedName("Search")
    val showSearchResults: List<ShowIdResponse>
)

data class ShowIdResponse(
    @field:SerializedName("imdbID")
    val imdbId: String
)