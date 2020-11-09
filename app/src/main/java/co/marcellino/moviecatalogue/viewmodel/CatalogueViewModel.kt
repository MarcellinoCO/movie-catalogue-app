package co.marcellino.moviecatalogue.viewmodel

import androidx.lifecycle.ViewModel
import co.marcellino.moviecatalogue.model.Movie
import co.marcellino.moviecatalogue.model.Show
import co.marcellino.moviecatalogue.utils.DataParser
import co.marcellino.moviecatalogue.utils.RawReader
import java.io.InputStream

class CatalogueViewModel : ViewModel() {

    lateinit var moviesList: List<Movie>
    fun isMoviesListInitialized(): Boolean = this::moviesList.isInitialized
    fun getMoviesList(inputStream: InputStream): List<Movie> {
        moviesList = DataParser.getMoviesList(RawReader.readFromRaw(inputStream))
        return moviesList
    }

    lateinit var showsList: List<Show>
    fun isShowsListInitialized(): Boolean = this::showsList.isInitialized
    fun getShowsList(inputStream: InputStream): List<Show> {
        showsList = DataParser.getShowsList(RawReader.readFromRaw(inputStream))
        return showsList
    }
}