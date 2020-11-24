package co.marcellino.moviecatalogue.data.source.remote.response.show

import com.google.gson.annotations.SerializedName

data class DiscoverShowResponse(
    @field:SerializedName("page")
    val page: Int = 0,

    @field:SerializedName("total_pages")
    val totalPages: Int = 0,

    @field:SerializedName("results")
    val showNames: List<ShowNameResponse> = listOf()
)

data class ShowNameResponse(
    @field:SerializedName("name")
    val name: String = ""
)