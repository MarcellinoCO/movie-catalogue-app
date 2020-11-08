package co.marcellino.moviecatalogue.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Show(
    val title: String = "",
    val year: String = "",

    val posterPath: String = "",
    val rating: String = "",

    val runtime: String = "",
    val genre: String = "",
    val plot: String = "",

    val writer: String = "",
    val actors: String = "",
    val awards: String = ""
) : Parcelable