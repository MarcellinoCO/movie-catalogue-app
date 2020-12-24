package co.marcellino.moviecatalogue.ui.favorites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.marcellino.moviecatalogue.R
import kotlinx.android.synthetic.main.activity_favorites.*

class FavoritesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        setSupportActionBar(toolbar_favorites)
        supportActionBar?.elevation = .0f
        toolbar_favorites.setNavigationOnClickListener { onBackPressed() }

        val favoritesPagerAdapter = FavoritesPagerAdapter(this, supportFragmentManager)
        vp_favorites.adapter = favoritesPagerAdapter
        tabs_favorites.setupWithViewPager(vp_favorites)
    }
}