package co.marcellino.moviecatalogue.ui.catalogue

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import co.marcellino.moviecatalogue.R
import co.marcellino.moviecatalogue.ui.favorites.FavoritesActivity
import kotlinx.android.synthetic.main.activity_catalogue.*

class CatalogueActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogue)

        setSupportActionBar(toolbar_catalogue)
        supportActionBar?.elevation = .0f

        val cataloguePagerAdapter = CataloguePagerAdapter(this, supportFragmentManager)
        vp_catalogue.adapter = cataloguePagerAdapter
        tabs_catalogue.setupWithViewPager(vp_catalogue)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_catalogue, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_go_to_favorites) {
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }
}