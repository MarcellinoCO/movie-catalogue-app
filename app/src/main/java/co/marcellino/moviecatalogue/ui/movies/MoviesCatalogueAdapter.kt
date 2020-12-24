package co.marcellino.moviecatalogue.ui.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.marcellino.moviecatalogue.R
import co.marcellino.moviecatalogue.data.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_catalogue.view.*

class MoviesCatalogueAdapter(val listener: (Movie) -> Unit) :
    PagedListAdapter<Movie, MoviesCatalogueAdapter.MovieHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }

    private val listMovies = ArrayList<Movie>()
    fun setMoviesList(newListMovies: List<Movie>?) {
        if (newListMovies == null) return

        listMovies.clear()
        listMovies.addAll(newListMovies)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_catalogue, parent, false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val movie = getItem(position) ?: Movie()
        holder.bind(movie)
    }

    inner class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            with(itemView) {
                tv_catalogue_title.text = movie.title
                tv_catalogue_year.text =
                    resources.getString(R.string.format_catalogue_year, movie.year)
                tv_catalogue_rating.text = movie.rating

                Glide.with(context).load(movie.posterPath).centerCrop().apply(
                    RequestOptions.placeholderOf(R.drawable.ic_image_loading)
                        .error(R.drawable.ic_error)
                ).into(iv_catalogue_poster)

                setOnClickListener {
                    listener(movie)
                }
            }
        }
    }
}