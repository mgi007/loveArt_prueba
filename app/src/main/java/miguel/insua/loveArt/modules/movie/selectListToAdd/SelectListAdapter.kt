package miguel.insua.loveArt.modules.movie.selectListToAdd

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import miguel.insua.loveArt.R



class SelectListAdapter(
    private val context: Context,
    private val itemClickListener: ListItemOnClickListener
    ): RecyclerView.Adapter<SelectListAdapter.ListViewHolder>() {

    private var dataList = mutableListOf<String>()

    interface ListItemOnClickListener {
        fun onItemClick(listName: String)
    }

    fun setListData(data: MutableList<String>) {
        dataList = data
    }

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bindView(list: String) {
            itemView.list_name.text = list
            itemView.setOnClickListener {
                itemClickListener.onItemClick(list)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.select_list_item, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val list: String = dataList[position]
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