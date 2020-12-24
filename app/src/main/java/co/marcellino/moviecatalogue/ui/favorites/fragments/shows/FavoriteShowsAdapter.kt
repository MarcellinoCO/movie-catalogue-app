package co.marcellino.moviecatalogue.ui.favorites.fragments.shows

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.marcellino.moviecatalogue.R
import co.marcellino.moviecatalogue.data.Show
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_catalogue.view.*

class FavoriteShowsAdapter(val listener: (Show) -> Unit) :
    PagedListAdapter<Show, FavoriteShowsAdapter.ShowHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Show>() {
            override fun areItemsTheSame(oldItem: Show, newItem: Show): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Show, newItem: Show): Boolean =
                oldItem == newItem
        }
    }

    private val listShows = ArrayList<Show>()
    fun setShowsList(newListShows: List<Show>?) {
        if (newListShows == null) return

        listShows.clear()
        listShows.addAll(newListShows)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_catalogue, parent, false)
        return ShowHolder(view)
    }

    override fun onBindViewHolder(holder: ShowHolder, position: Int) {
        val show = listShows[position]
        holder.bind(show)
    }

    override fun getItemCount(): Int = listShows.size

    inner class ShowHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(show: Show) {
            with(itemView) {
                tv_catalogue_title.text = show.title
                tv_catalogue_year.text =
                    resources.getString(R.string.format_catalogue_year, show.year)
                tv_catalogue_rating.text = show.rating

                Glide.with(context).load(show.posterPath).centerCrop().apply(
                    RequestOptions.placeholderOf(R.drawable.ic_image_loading)
                        .error(R.drawable.ic_error)
                ).into(iv_catalogue_poster)

                setOnClickListener {
                    listener(show)
                }
            }
        }
    }
}