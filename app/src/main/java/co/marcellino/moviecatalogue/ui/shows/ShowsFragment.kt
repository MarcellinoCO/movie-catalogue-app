package co.marcellino.moviecatalogue.ui.shows

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import co.marcellino.moviecatalogue.R
import co.marcellino.moviecatalogue.model.Show
import co.marcellino.moviecatalogue.ui.catalogue.CatalogueListener
import co.marcellino.moviecatalogue.ui.details.DetailsActivity
import co.marcellino.moviecatalogue.utils.DataParser
import co.marcellino.moviecatalogue.utils.RawReader
import kotlinx.android.synthetic.main.fragment_shows.*

class ShowsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = layoutInflater.inflate(R.layout.fragment_shows, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val showsCatalogueAdapter = ShowsCatalogueAdapter(object : CatalogueListener {
            override fun showClicked(show: Show) {
                val intent = Intent(context, DetailsActivity::class.java).apply {
                    putExtra(DetailsActivity.EXTRA_TYPE, DetailsActivity.TYPE_SHOW)
                    putExtra(DetailsActivity.EXTRA_ENTITY, show)
                }
                startActivity(intent)
            }
        }).apply {
            setShowsList(
                DataParser.getShowsList(
                    RawReader.readFromRaw(resources.openRawResource(R.raw.shows))
                ).shuffled()
            )
        }

        with(rv_shows) {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)

            adapter = showsCatalogueAdapter
        }
    }
}