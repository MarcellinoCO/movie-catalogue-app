package co.marcellino.moviecatalogue.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import co.marcellino.moviecatalogue.data.Movie
import co.marcellino.moviecatalogue.data.Show
import co.marcellino.moviecatalogue.data.source.Repository
import co.marcellino.moviecatalogue.vo.Resource

class CatalogueViewModel(private val repository: Repository) : ViewModel() {

    lateinit var moviesList: LiveData<Resource<List<Movie>>>
    fun loadMoviesList(): LiveData<Resource<List<Movie>>> {
        if (!this::moviesList.isInitialized) moviesList = repository.discoverMovies()

        return moviesList
    }

    lateinit var showsList: LiveData<Resource<List<Show>>>
    fun loadShowsList(): LiveData<Resource<List<Show>>> {
        if (!this::moviesList.isInitialized) showsList = repository.discoverShows()

        return showsList
    }
}