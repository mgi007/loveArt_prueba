package miguel.insua.loveArt.modules.home


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.android.synthetic.main.little_item.view.*
import miguel.insua.loveArt.R
import miguel.insua.loveArt.model.Media
import miguel.insua.loveArt.model.MediaResponse
import miguel.insua.loveArt.modules.lists.ListAdapter
import kotlin.math.absoluteValue


class HomeAdapter(private val context: Context,
                  private val itemClickListener: ItemOnClickListener
): RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var dataList = mutableListOf<Media>()

    interface ItemOnClickListener {
        fun onItemClick(media: Media)
    }

    fun setListData(data: MutableList<Media>) {
        dataList = data
    }

    inner class HomeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bindView(media: Media) {
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
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.little_item, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val media: Media = dataList[position]
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