package co.marcellino.moviecatalogue.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import co.marcellino.moviecatalogue.data.Movie
import co.marcellino.moviecatalogue.data.Show
import co.marcellino.moviecatalogue.data.source.Repository

class CatalogueViewModel(private val repository: Repository) : ViewModel() {

    lateinit var moviesList: LiveData<List<Movie>>
    fun loadMoviesList(): LiveData<List<Movie>> {
        if (!this::moviesList.isInitialized) moviesList = repository.discoverMovies()

        return moviesList
    }

    lateinit var showsList: LiveData<List<Show>>
    fun loadShowsList(): LiveData<List<Show>> {
        if (!this::moviesList.isInitialized) showsList = repository.discoverShows()

        return showsList
    }
}