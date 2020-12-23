package co.marcellino.moviecatalogue.data

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "show")
data class Show(
    @PrimaryKey
    @NonNull
    val title: String = "",
    val year: String = "",

    val posterPath: String = "",
    val rating: String = "",

    val runtime: String = "",
    val genre: String = "",
    val plot: String = "",

    val writer: String = "",
    val actors: String = "",
    val awards: String = "",

    var isFavorite: Boolean = false
) : Parcelable