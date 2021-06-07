package miguel.insua.loveArt.modules.listMovies

import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_movies_item.view.*
import kotlinx.android.synthetic.main.little_item.view.*
import kotlinx.android.synthetic.main.little_item.view.item_image
import kotlinx.android.synthetic.main.little_item.view.item_og_language
import kotlinx.android.synthetic.main.little_item.view.item_rating
import kotlinx.android.synthetic.main.little_item.view.item_release_date
import kotlinx.android.synthetic.main.little_item.view.item_tittle
import kotlinx.android.synthetic.main.little_item.view.item_vote_count
import miguel.insua.loveArt.R
import miguel.insua.loveArt.model.Media
import miguel.insua.loveArt.model.MediaRequestFirebase
import kotlin.math.absoluteValue

class ListMoviesAdapter(private val context: Context,
                        private val itemClickListener: ItemOnClickListener
): RecyclerView.Adapter<ListMoviesAdapter.HomeViewHolder>() {

    private var dataList = mutableListOf<MediaRequestFirebase>()

    interface ItemOnClickListener {
        fun onItemClick(media: MediaRequestFirebase)
        fun onDeleteClick(media: MediaRequestFirebase)
    }

    fun setListData(data: MutableList<MediaRequestFirebase>) {
        dataList = data
    }

    inner class HomeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bindView(media: MediaRequestFirebase) {
            Glide.with(context).load("https://image.tmdb.org/t/p/w500" + media.imageUrl).into(itemView.item_image)
            itemView.item_tittle.text = media.tittle
            itemView.item_release_date.text = context.resources.getString(R.string.release_date.absoluteValue) +
                    "   " + media.releaseDate
            itemView.item_og_language.text = context.resources.getString(R.string.original_language) +
                    "   " + media.og_language
            itemView.item_vote_count.text = context.resources.getString(R.string.vote_count) +
                    "   " + media.vote_count.toString()
            itemView.item_rating.rating = (media.rating?.toFloat()!!)/2

            itemView.setOnClickListener {
                itemClickListener.onItemClick(media)
            }

            itemView.delete_movie_to_list.setOnClickListener {
                itemClickListener.onDeleteClick(media)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_movies_item, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val media: MediaRequestFirebase = dataList[position]
        holder.bindView(media)
    }

    override fun getItemCount(): Int {
        return if (dataList.size > 0) {
            dataList.size
        } else {
            0
        }
    }
}