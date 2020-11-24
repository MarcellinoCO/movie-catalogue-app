package co.marcellino.moviecatalogue.data.source.remote.response.show

import com.google.gson.annotations.SerializedName

data class ShowSearchResponse(
    @field:SerializedName("totalResults")
    val totalResults: Int = 0,

    @field:SerializedName("Search")
    val showSearchResults: List<ShowIdResponse> = listOf()
)