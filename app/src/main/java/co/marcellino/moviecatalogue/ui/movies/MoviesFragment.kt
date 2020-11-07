package co.marcellino.moviecatalogue.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import co.marcellino.moviecatalogue.R
import co.marcellino.moviecatalogue.utils.DataParser
import co.marcellino.moviecatalogue.utils.RawReader
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = layoutInflater.inflate(R.layout.fragment_movies, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val movieCatalogueAdapter = MoviesCatalogueAdapter()
        movieCatalogueAdapter.setMoviesList(
            DataParser.getMoviesList(
                RawReader.readFromRaw(resources.openRawResource(R.raw.movies))
            ).shuffled()
        )

        with(rv_movies) {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)

            adapter = movieCatalogueAdapter
        }
    }
}