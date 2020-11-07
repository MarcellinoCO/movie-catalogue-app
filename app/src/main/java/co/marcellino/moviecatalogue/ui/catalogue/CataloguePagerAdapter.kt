package co.marcellino.moviecatalogue.ui.catalogue

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import co.marcellino.moviecatalogue.R
import co.marcellino.moviecatalogue.ui.movies.MoviesFragment
import co.marcellino.moviecatalogue.ui.shows.ShowsFragment

class CataloguePagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.title_movies, R.string.title_shows)
    }

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> MoviesFragment()
        1 -> ShowsFragment()
        else -> Fragment()
    }

    override fun getPageTitle(position: Int): CharSequence? =
        context.resources.getString(TAB_TITLES[position])

    override fun getCount(): Int = 2
}