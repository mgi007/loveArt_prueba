package miguel.insua.loveArt.modules.home

import android.app.Application
import com.google.android.material.internal.ContextUtils.getActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import miguel.insua.loveArt.api.TMDbService
import miguel.insua.loveArt.application.App
import miguel.insua.loveArt.model.Media
import miguel.insua.loveArt.model.MediaResponse
import miguel.insua.loveArt.model.Movie
import miguel.insua.loveArt.modules.base.BaseViewModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeActivityViewModel(app: Application) : BaseViewModel(app) {

    init {
        (app as? App)?.component?.inject(this)
    }


    lateinit var uid: String

    var fragmentState: FragmentState = FragmentState.HOME

}