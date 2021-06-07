package miguel.insua.loveArt.modules.listMovies

import android.app.Application
import android.provider.MediaStore
import com.google.firebase.firestore.FirebaseFirestore
import miguel.insua.loveArt.application.App
import miguel.insua.loveArt.model.Media
import miguel.insua.loveArt.model.MediaRequestFirebase
import miguel.insua.loveArt.modules.base.BaseViewModel

class ListMoviesViewModel(app: Application) : BaseViewModel(app) {

    lateinit var uid: String

    lateinit var listName: String

    lateinit var adapter: ListMoviesAdapter

    val db = FirebaseFirestore.getInstance()

    lateinit var back: () -> Unit

    init {
        (app as? App)?.component?.inject(this)
    }

    fun refreshData(movies: ArrayList<MediaRequestFirebase>) {
        val list = mutableListOf<MediaRequestFirebase>()
        if (movies != null) {
            list.addAll(movies)
            adapter.setListData(list)
            adapter.notifyDataSetChanged()
        }
    }
}