package co.marcellino.moviecatalogue.ui.favorites.fragments.shows

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
import co.marcellino.moviecatalogue.data.Show
import co.marcellino.moviecatalogue.ui.details.DetailsActivity
import co.marcellino.moviecatalogue.viewmodel.DetailsViewModel
import co.marcellino.moviecatalogue.viewmodel.FavoritesViewModel
import co.marcellino.moviecatalogue.viewmodel.ViewModelFactory
import co.marcellino.moviecatalogue.vo.Status
import kotlinx.android.synthetic.main.fragment_shows.*

class FavoriteShowsFragment : Fragment() {

    private lateinit var viewModel: FavoritesViewModel
    private lateinit var showsList: List<Show>

    private lateinit var showsCatalogueAdapter: FavoriteShowsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = layoutInflater.inflate(R.layout.fragment_shows, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (activity == null) return

        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[FavoritesViewModel::class.java]

        showsCatalogueAdapter = FavoriteShowsAdapter { show ->
            val intent = Intent(context, DetailsActivity::class.java).apply {
                putExtra(DetailsActivity.EXTRA_TYPE, DetailsViewModel.TYPE_SHOW)
                putExtra(DetailsActivity.EXTRA_ENTITY, show)
            }
            startActivity(intent)
        }

        with(rv_shows) {
            val orientation = this@FavoriteShowsFragment.resources.configuration.orientation
            val spanCount = if (orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 3

            layoutManager =
                StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)

            adapter = showsCatalogueAdapter
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.loadShowsList().observe(viewLifecycleOwner, Observer { shows ->
            if (shows?.data == null) return@Observer

            when (shows.status) {
                Status.LOADING -> pb_shows.visibility = View.VISIBLE
                Status.SUCESS -> {
                    pb_shows.visibility = View.GONE

                    val validShows = ArrayList<Show>()
                    for (show in shows.data) {
                        if (!show.isEmpty()) validShows.add(show)
                    }

                    showsList = validShows
                    pb_shows.visibility = View.GONE

                    showsCatalogueAdapter.setShowsList(validShows)
                    showsCatalogueAdapter.notifyDataSetChanged()

                    if (shows.data.isEmpty()) tv_shows_empty.visibility = View.VISIBLE
                    else tv_shows_empty.visibility = View.GONE
                }
                Status.ERROR -> {
                    pb_shows.visibility = View.GONE
                    tv_shows_empty.visibility = View.VISIBLE

                    Toast.makeText(context, "An Error Occurred", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}