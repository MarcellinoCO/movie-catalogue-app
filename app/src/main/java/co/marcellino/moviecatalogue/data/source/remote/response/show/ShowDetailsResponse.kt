package co.marcellino.moviecatalogue.data.source.remote.response.show

import com.google.gson.annotations.SerializedName

data class ShowDetailsResponse(
    @field:SerializedName("Title")
    val title: String = "",
    @field:SerializedName("Year")
    val year: String = "",

    @field:SerializedName("Poster")
    val posterPath: String = "",

    @field:SerializedName("Ratings")
    val rating: List<ShowRatingsResponse> = listOf(ShowRatingsResponse()),

    @field:SerializedName("Runtime")
    val runtime: String = "",
    @field:SerializedName("Genre")
    val genre: String = "",
    @field:SerializedName("Plot")
    val plot: String = "",

    @field:SerializedName("Writer")
    val writer: String = "",
    @field:SerializedName("Actors")
    val actors: String = "",

    @field:SerializedName("Awards")
    val awards: String = ""
)