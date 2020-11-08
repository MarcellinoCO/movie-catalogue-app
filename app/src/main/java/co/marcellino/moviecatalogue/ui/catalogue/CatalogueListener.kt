package co.marcellino.moviecatalogue.ui.catalogue

import co.marcellino.moviecatalogue.model.Movie
import co.marcellino.moviecatalogue.model.Show

interface CatalogueListener {

    fun movieClicked(movie: Movie) {}

    fun showClicked(show: Show) {}
}