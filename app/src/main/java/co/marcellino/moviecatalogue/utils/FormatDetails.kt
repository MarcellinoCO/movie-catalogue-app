package co.marcellino.moviecatalogue.utils

object FormatDetails {

    fun getRuntimeFormat(runtime: String): String {
        // Format of runtime is '### min'
        // Modify it to become '#h ##m'

        val runtimeInt = runtime.split(' ')[0].toInt()
        val hour = runtimeInt / 60
        val minute = runtimeInt % 60

        return if (hour == 0) "${minute}m"
        else "${hour}h ${minute}m"
    }

    fun getGenreFormat(genre: String): String {
        // Format of genre is '####, ####, ####'
        // Modify it to become '#### / #### / ####' maximum 3 genres.

        var genresList = genre.split(", ")
        if (genresList.size > 3) genresList = genresList.subList(0, 3)

        return genresList.joinToString(separator = " / ")
    }

    fun getCastsFormat(casts: String): String {
        // Format of casts are '####, ####'
        // Modify it to become '####\n####\n####'

        val castsList = casts.split(", ")
        return castsList.joinToString(separator = "\n")
    }
}