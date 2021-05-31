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
import miguel.insua.loveArt.model.MovieLanguages


class MovieLanguagesAdapter(
    private val context: Context,
    ): RecyclerView.Adapter<MovieLanguagesAdapter.MovieLanguagesViewHolder>() {

    private var dataList = mutableListOf<MovieLanguages>()

    fun setListData(data: MutableList<MovieLanguages>) {
        dataList = data
    }

    inner class MovieLanguagesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bindView(movieLanguages: MovieLanguages) {
            itemView.item_movie_genre.text = movieLanguages.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieLanguagesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.movie_genre, parent, false)
        return MovieLanguagesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieLanguagesViewHolder, position: Int) {
        val list: MovieLanguages = dataList[position]
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