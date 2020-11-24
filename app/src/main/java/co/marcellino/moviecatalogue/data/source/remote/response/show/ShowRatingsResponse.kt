package co.marcellino.moviecatalogue.data.source.remote.response.show

import com.google.gson.annotations.SerializedName

data class ShowRatingsResponse(
    @field:SerializedName("Value")
    val rating: String = ""
)