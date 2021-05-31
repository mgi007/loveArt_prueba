package miguel.insua.loveArt.modules.siEmail

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import miguel.insua.loveArt.application.App
import miguel.insua.loveArt.modules.base.BaseViewModel

class SiEmailViewModel(app: Application) : BaseViewModel(app) {

    lateinit var signIn: () -> Unit

    lateinit var back: () -> Unit

    init {
        (app as? App)?.component?.inject(this)
    }





}