package co.marcellino.moviecatalogue.ui.movies

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import co.marcellino.moviecatalogue.R
import co.marcellino.moviecatalogue.model.Movie
import co.marcellino.moviecatalogue.ui.catalogue.CatalogueListener
import co.marcellino.moviecatalogue.ui.details.DetailsActivity
import co.marcellino.moviecatalogue.viewmodel.CatalogueViewModel
import co.marcellino.moviecatalogue.viewmodel.DetailsViewModel.Companion.TYPE_MOVIE
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFragment : Fragment() {

    private lateinit var viewModel: CatalogueViewModel
    private lateinit var moviesList: List<Movie>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = layoutInflater.inflate(R.layout.fragment_movies, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.NewInstanceFactory()
        )[CatalogueViewModel::class.java]

        moviesList = if (viewModel.isMoviesListInitialized()) {
            viewModel.moviesList
        } else viewModel.getMoviesList(resources.openRawResource(R.raw.movies))

        val movieCatalogueAdapter = MoviesCatalogueAdapter(object : CatalogueListener {
            override fun movieClicked(movie: Movie) {
                val intent = Intent(context, DetailsActivity::class.java).apply {
                    putExtra(DetailsActivity.EXTRA_TYPE, TYPE_MOVIE)
                    putExtra(DetailsActivity.EXTRA_ENTITY, movie)
                }
                startActivity(intent)
            }
        }).apply {
            setMoviesList(moviesList)
        }

        with(rv_movies) {
            val orientation = this@MoviesFragment.resources.configuration.orientation
            val spanCount = if (orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 3

            layoutManager =
                StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)

            adapter = movieCatalogueAdapter
        }
    }
}