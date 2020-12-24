package co.marcellino.moviecatalogue.ui.details

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import co.marcellino.moviecatalogue.R
import co.marcellino.moviecatalogue.data.Movie
import co.marcellino.moviecatalogue.data.Show
import co.marcellino.moviecatalogue.utils.FormatDetails.getCastsFormat
import co.marcellino.moviecatalogue.utils.FormatDetails.getGenreFormat
import co.marcellino.moviecatalogue.utils.FormatDetails.getRuntimeFormat
import co.marcellino.moviecatalogue.viewmodel.DetailsViewModel
import co.marcellino.moviecatalogue.viewmodel.DetailsViewModel.Companion.TYPE_MOVIE
import co.marcellino.moviecatalogue.viewmodel.DetailsViewModel.Companion.TYPE_SHOW
import co.marcellino.moviecatalogue.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.content_details.*

class DetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TYPE = "extra_type"
        const val EXTRA_ENTITY = "extra_entity"
    }

    private lateinit var viewModel: DetailsViewModel
    private var menu: Menu? = null

    private var type: Int? = null

    private lateinit var movie: Movie
    private lateinit var show: Show

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailsViewModel::class.java]

        setSupportActionBar(toolbar_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_details.setNavigationOnClickListener {
            onBackPressed()
        }

        val extras = intent.extras
        if (extras != null) {
            type =
                if (viewModel.isTypeInitialized()) viewModel.type
                else extras.getInt(EXTRA_TYPE)
            viewModel.type = type as Int

            supportActionBar?.title =
                resources.getString(
                    if (type == TYPE_MOVIE) R.string.title_movie_details
                    else R.string.title_show_details
                )

            if (type == TYPE_MOVIE) {
                movie = if (viewModel.isMovieInitialized()) viewModel.movie
                else extras.getParcelable(EXTRA_ENTITY) ?: Movie()

                viewModel.movie = movie
                populateMovieDetails(movie)
            } else {
                show = if (viewModel.isShowInitialized()) viewModel.show
                else extras.getParcelable(EXTRA_ENTITY) ?: Show()

                viewModel.show = show
                populateShowDetails(show)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_details, menu)
        this.menu = menu

        if (type == TYPE_MOVIE) setFavoriteState(movie.isFavorite)
        else if (type == TYPE_SHOW) setFavoriteState(show.isFavorite)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add_favorite) {
            viewModel.toggleFavorite()

            if (type == TYPE_MOVIE) {
                movie = viewModel.movie
                setFavoriteState(movie.isFavorite)
            } else if (type == TYPE_SHOW) {
                show = viewModel.show
                setFavoriteState(show.isFavorite)
            }

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun populateMovieDetails(movie: Movie) {
        tv_details_title.text = movie.title
        tv_details_year.text = movie.year

        Glide.with(this).load(movie.posterPath).centerCrop().apply(
            RequestOptions.placeholderOf(R.drawable.ic_image_loading)
                .error(R.drawable.ic_error)
        ).into(iv_details_poster)
        tv_details_rating.text = movie.rating

        tv_details_runtime.text = getRuntimeFormat(movie.runtime)

        tv_details_genre.text = getGenreFormat(movie.genre)
        tv_details_plot.text = movie.plot

        tv_details_director.text = getCastsFormat(movie.director)
        tv_details_writer.text = getCastsFormat(movie.writer)
        tv_details_actors.text = getCastsFormat(movie.actors)

        tv_details_awards.text = movie.awards
    }

    private fun populateShowDetails(show: Show) {
        tv_details_title.text = show.title
        tv_details_year.text = show.year

        Glide.with(this).load(show.posterPath).centerCrop().apply(
            RequestOptions.placeholderOf(R.drawable.ic_image_loading)
                .error(R.drawable.ic_error)
        ).into(iv_details_poster)
        tv_details_rating.text = show.rating

        tv_details_runtime.text = getRuntimeFormat(show.runtime)
        tv_details_genre.text = getGenreFormat(show.genre)
        tv_details_plot.text = show.plot

        tv_details_header_director.visibility = View.GONE
        tv_details_director.visibility = View.GONE

        tv_details_writer.text = getCastsFormat(show.writer)
        tv_details_actors.text = getCastsFormat(show.actors)

        tv_details_awards.text = show.awards
    }

    private fun setFavoriteState(isFavorite: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.menu_add_favorite)

        menuItem?.icon =
            if (isFavorite) ContextCompat.getDrawable(this, R.drawable.ic_favorite_fill)
            else ContextCompat.getDrawable(this, R.drawable.ic_favorite_border)
    }
}