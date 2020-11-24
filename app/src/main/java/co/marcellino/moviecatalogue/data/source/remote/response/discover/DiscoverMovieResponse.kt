package co.marcellino.moviecatalogue.data.source.remote.response.discover

import com.google.gson.annotations.SerializedName

data class DiscoverMovieResponse(
    @field:SerializedName("page")
    val page: Int,

    @field:SerializedName("total_pages")
    val totalPages: Int,

    @field:SerializedName("results")
    val movieResults: List<MovieSummaryResponse>
)

data class MovieSummaryResponse(
    @field:SerializedName("id")
    val id: String
)