package co.marcellino.moviecatalogue.ui.favorites.fragments.movies

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import co.marcellino.moviecatalogue.R
import co.marcellino.moviecatalogue.data.Movie
import co.marcellino.moviecatalogue.ui.details.DetailsActivity
import co.marcellino.moviecatalogue.viewmodel.DetailsViewModel
import co.marcellino.moviecatalogue.viewmodel.FavoritesViewModel
import co.marcellino.moviecatalogue.viewmodel.ViewModelFactory
import co.marcellino.moviecatalogue.vo.Status
import kotlinx.android.synthetic.main.fragment_movies.*

class FavoriteMoviesFragment : Fragment() {

    private lateinit var viewModel: FavoritesViewModel
    private lateinit var moviesList: List<Movie>

    private lateinit var moviesCatalogueAdapter: FavoriteMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = layoutInflater.inflate(R.layout.fragment_movies, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (activity == null) return

        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[FavoritesViewModel::class.java]

        moviesCatalogueAdapter = FavoriteMoviesAdapter { movie ->
            val intent = Intent(context, DetailsActivity::class.java).apply {
                putExtra(DetailsActivity.EXTRA_TYPE, DetailsViewModel.TYPE_MOVIE)
                putExtra(DetailsActivity.EXTRA_ENTITY, movie)
            }
            startActivity(intent)
        }

        with(rv_movies) {
            val orientation = this@FavoriteMoviesFragment.resources.configuration.orientation
            val spanCount = if (orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 3

            layoutManager =
                StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)

            adapter = moviesCatalogueAdapter
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.loadMoviesList().observe(viewLifecycleOwner, Observer { movies ->
            if (movies?.data == null) return@Observer

            when (movies.status) {
                Status.LOADING -> pb_movies.visibility = View.VISIBLE
                Status.SUCESS -> {
                    pb_movies.visibility = View.GONE

                    val validMovies = ArrayList<Movie>()
                    for (movie in movies.data) {
                        if (!movie.isEmpty()) validMovies.add(movie)
                    }

                    moviesList = validMovies
                    pb_movies.visibility = View.GONE

                    moviesCatalogueAdapter.setMoviesList(validMovies)
                    moviesCatalogueAdapter.notifyDataSetChanged()

                    if (movies.data.isEmpty()) tv_movies_empty.visibility = View.VISIBLE
                    else tv_movies_empty.visibility = View.GONE
                }
                Status.ERROR -> {
                    pb_movies.visibility = View.GONE
                    tv_movies_empty.visibility = View.VISIBLE

                    Toast.makeText(context, "An Error Occurred", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}