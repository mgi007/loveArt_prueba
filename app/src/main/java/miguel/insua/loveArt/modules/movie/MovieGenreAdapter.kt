package miguel.insua.loveArt.modules.movie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.android.synthetic.main.movie_genre.view.*
import miguel.insua.loveArt.R
import miguel.insua.loveArt.model.MovieGenre


class MovieGenreAdapter(
    private val context: Context,
    ): RecyclerView.Adapter<MovieGenreAdapter.MovieGenreViewHolder>() {

    private var dataList = mutableListOf<MovieGenre>()

    fun setListData(data: MutableList<MovieGenre>) {
        dataList = data
    }

    inner class MovieGenreViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bindView(movieGenre: MovieGenre) {
            itemView.item_movie_genre.text = movieGenre.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieGenreViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.movie_genre, parent, false)
        return MovieGenreViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieGenreViewHolder, position: Int) {
        val list: MovieGenre = dataList[position]
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