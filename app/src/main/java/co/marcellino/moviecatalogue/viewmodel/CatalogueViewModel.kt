package co.marcellino.moviecatalogue.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import co.marcellino.moviecatalogue.data.Movie
import co.marcellino.moviecatalogue.data.Show
import co.marcellino.moviecatalogue.data.source.Repository
import co.marcellino.moviecatalogue.vo.Resource

class CatalogueViewModel(private val repository: Repository) : ViewModel() {

    lateinit var moviesList: LiveData<Resource<PagedList<Movie>>>
    fun loadMoviesList(): LiveData<Resource<PagedList<Movie>>> {
        moviesList = repository.discoverMovies()
        return moviesList
    }

    lateinit var showsList: LiveData<Resource<PagedList<Show>>>
    fun loadShowsList(): LiveData<Resource<PagedList<Show>>> {
        showsList = repository.discoverShows()
        return showsList
    }
}