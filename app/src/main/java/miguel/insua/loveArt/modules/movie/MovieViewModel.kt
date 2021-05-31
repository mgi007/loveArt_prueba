package miguel.insua.loveArt.modules.movie

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import miguel.insua.loveArt.api.TMDbService
import miguel.insua.loveArt.application.App
import miguel.insua.loveArt.model.Media
import miguel.insua.loveArt.model.Movie
import miguel.insua.loveArt.model.MovieGenre
import miguel.insua.loveArt.modules.base.BaseViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieViewModel(app: Application) : BaseViewModel(app) {

    lateinit var back: () -> Unit

    lateinit var request: () -> Unit

    lateinit var recommendationMovies: () -> Unit

    lateinit var similarMovies: () -> Unit

    var movie: Movie? = null


    init {
        (app as? App)?.component?.inject(this)
    }




}