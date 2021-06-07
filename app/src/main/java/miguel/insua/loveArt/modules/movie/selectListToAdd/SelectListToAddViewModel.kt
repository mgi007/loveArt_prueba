package miguel.insua.loveArt.modules.movie.selectListToAdd

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import miguel.insua.loveArt.application.App
import miguel.insua.loveArt.modules.base.BaseViewModel

class SelectListToAddViewModel(app: Application) : BaseViewModel(app) {

    lateinit var back: () -> Unit

    private val auth = FirebaseAuth.getInstance()

    val db = FirebaseFirestore.getInstance()

    init {
        (app as? App)?.component?.inject(this)
    }
}