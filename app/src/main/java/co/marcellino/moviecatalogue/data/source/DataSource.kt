package co.marcellino.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import co.marcellino.moviecatalogue.data.Movie
import co.marcellino.moviecatalogue.data.Show

interface DataSource {
    fun discoverMovies(): LiveData<List<Movie>>
    fun discoverShows(): LiveData<List<Show>>
}