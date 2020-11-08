package co.marcellino.moviecatalogue.ui.shows

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
import co.marcellino.moviecatalogue.model.Show
import co.marcellino.moviecatalogue.ui.catalogue.CatalogueListener
import co.marcellino.moviecatalogue.ui.details.DetailsActivity
import co.marcellino.moviecatalogue.viewmodel.CatalogueViewModel
import co.marcellino.moviecatalogue.viewmodel.DetailsViewModel.Companion.TYPE_SHOW
import kotlinx.android.synthetic.main.fragment_shows.*

class ShowsFragment : Fragment() {

    private lateinit var viewModel: CatalogueViewModel
    private lateinit var showsList: List<Show>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = layoutInflater.inflate(R.layout.fragment_shows, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.NewInstanceFactory()
        )[CatalogueViewModel::class.java]

        showsList = if (viewModel.isShowsListInitialized()) {
            viewModel.showsList
        } else viewModel.getShowsList(resources.openRawResource(R.raw.shows))

        val showsCatalogueAdapter = ShowsCatalogueAdapter(object : CatalogueListener {
            override fun showClicked(show: Show) {
                val intent = Intent(context, DetailsActivity::class.java).apply {
                    putExtra(DetailsActivity.EXTRA_TYPE, TYPE_SHOW)
                    putExtra(DetailsActivity.EXTRA_ENTITY, show)
                }
                startActivity(intent)
            }
        }).apply {
            setShowsList(showsList)
        }

        with(rv_shows) {
            val orientation = this@ShowsFragment.resources.configuration.orientation
            val spanCount = if (orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 3

            layoutManager =
                StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)

            adapter = showsCatalogueAdapter
        }
    }
}