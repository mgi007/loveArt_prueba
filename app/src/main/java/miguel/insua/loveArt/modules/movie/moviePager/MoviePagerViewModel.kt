package miguel.insua.loveArt.modules.movie.moviePager

import android.app.Application
import miguel.insua.loveArt.application.App
import miguel.insua.loveArt.model.Media
import miguel.insua.loveArt.model.Movie
import miguel.insua.loveArt.modules.base.BaseViewModel
import miguel.insua.loveArt.modules.home.FragmentState
import miguel.insua.loveArt.modules.home.HomeAdapter

class MoviePagerViewModel(app: Application) : BaseViewModel(app) {

    lateinit var adapter: HomeAdapter

    lateinit var movie: Movie

    fun refreshData(listMedia: List<Media>) {
        val list: MutableList<Media> = mutableListOf<Media>()
        if (!listMedia.isNullOrEmpty()) {
            list.clear()
            list.addAll(listMedia)
            adapter.setListData(list)
            adapter.notifyDataSetChanged()
        }
    }

    init {
        (app as? App)?.component?.inject(this)
    }
}