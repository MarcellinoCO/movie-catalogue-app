package co.marcellino.moviecatalogue.data.source.remote.response.discover

import com.google.gson.annotations.SerializedName

data class DiscoverShowResponse(
    @field:SerializedName("page")
    val page: Int,

    @field:SerializedName("total_pages")
    val totalPages: Int,

    @field:SerializedName("results")
    val showNames: List<ShowNameResponse>
)

data class ShowNameResponse(
    @field:SerializedName("name")
    val name: String
)