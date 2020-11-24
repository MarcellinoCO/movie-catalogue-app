package co.marcellino.moviecatalogue.data.source.remote.response.movie

import com.google.gson.annotations.SerializedName

data class DiscoverMovieResponse(
    @field:SerializedName("page")
    val page: Int = 0,

    @field:SerializedName("total_pages")
    val totalPages: Int = 0,

    @field:SerializedName("results")
    val movieResults: List<MovieSummaryResponse> = listOf()
)

data class MovieSummaryResponse(
    @field:SerializedName("id")
    val id: String = ""
)