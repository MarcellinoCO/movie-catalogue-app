package co.marcellino.moviecatalogue.model

data class Show(
    val title: String,
    val year: String,

    val posterPath: String,
    val rating: String,

    val runtime: String,
    val plot: String,

    val writer: String,
    val actors: String,
    val awards: String
)