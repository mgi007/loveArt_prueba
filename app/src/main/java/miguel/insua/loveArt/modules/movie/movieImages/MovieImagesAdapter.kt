package miguel.insua.loveArt.modules.movie.movieImages

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.android.synthetic.main.little_item.view.*
import kotlinx.android.synthetic.main.movie_genre.view.*
import kotlinx.android.synthetic.main.movie_image_item.view.*
import miguel.insua.loveArt.R
import miguel.insua.loveArt.model.MovieGenre
import miguel.insua.loveArt.model.MovieImages
import miguel.insua.loveArt.model.MovieImagesCollection


class MovieImagesAdapter(
    private val context: Context,
    ): RecyclerView.Adapter<MovieImagesAdapter.MovieImagesViewHolder>() {

    private var dataList = mutableListOf<MovieImagesCollection>()

    fun setListData(data: MutableList<MovieImagesCollection>) {
        dataList = data
    }

    inner class MovieImagesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bindView(movieImagesCollection: MovieImagesCollection) {
            Glide.with(context).load("https://image.tmdb.org/t/p/w500" + movieImagesCollection.filePath).into(itemView.movie_image)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieImagesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.movie_image_item, parent, false)
        return MovieImagesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieImagesViewHolder, position: Int) {
        val list: MovieImagesCollection = dataList[position]
        holder.bindView(list)
    }

    override fun getItemCount(): Int {
        return if (dataList.size > 0) {
            dataList.size
        } else {
            0
        }
    }

}