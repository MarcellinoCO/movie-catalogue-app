package co.marcellino.moviecatalogue.ui.catalogue

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.marcellino.moviecatalogue.R
import kotlinx.android.synthetic.main.activity_catalogue.*

class CatalogueActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogue)

        val cataloguePagerAdapter = CataloguePagerAdapter(this, supportFragmentManager)
        vp_catalogue.adapter = cataloguePagerAdapter
        tabs_catalogue.setupWithViewPager(vp_catalogue)

        supportActionBar?.elevation = .0f
    }
}