package co.marcellino.moviecatalogue.data.source.remote.response.details

import com.google.gson.annotations.SerializedName

data class ShowDetailsResponse(
    @field:SerializedName("Title")
    val title: String,
    @field:SerializedName("Year")
    val year: String,

    @field:SerializedName("Poster")
    val posterPath: String,

    @field:SerializedName("Ratings")
    val rating: List<MovieRatingsResponse>,

    @field:SerializedName("Runtime")
    val runtime: String,
    @field:SerializedName("Genre")
    val genre: String,
    @field:SerializedName("Plot")
    val plot: String,

    @field:SerializedName("Writer")
    val writer: String,
    @field:SerializedName("Actors")
    val actors: String = "",

    @field:SerializedName("Awards")
    val awards: String = ""
)

data class ShowRatingsResponse(
    @field:SerializedName("Value")
    val rating: String
)