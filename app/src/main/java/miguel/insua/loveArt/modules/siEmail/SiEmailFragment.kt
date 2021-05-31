package miguel.insua.loveArt.modules.siEmail


import Valid
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_login.etEmail
import kotlinx.android.synthetic.main.fragment_login.etPassword
import kotlinx.android.synthetic.main.fragment_si_email.*
import miguel.insua.loveArt.R
import miguel.insua.loveArt.api.FirebaseApiManager
import miguel.insua.loveArt.databinding.FragmentSiEmailBinding
import miguel.insua.loveArt.modules.base.BaseFragment
import miguel.insua.loveArt.modules.home.HomeActivity
import miguel.insua.loveArt.modules.start.StartFragment



class SiEmailFragment : BaseFragment<SiEmailViewModel, FragmentSiEmailBinding>(
    SiEmailViewModel::class.java
) {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_si_email
    }

    override fun viewCreated(view: View?) {
        mBinding.viewModel = viewModel
        viewModel.signIn = ::signIn
        viewModel.back = ::back
    }


    private fun validSignInTasks(email: String, password: String, confirmPassword: String): ArrayList<Boolean> {
        var valid: Valid = Valid
        var ok: ArrayList<Boolean> = arrayListOf<Boolean>()

        if ( valid.isEmailValid(email) ) {
            ok.add(true)
        } else {
            ok.add(false)
        }

        if (valid.isPasswordValid(password) ){
            ok.add(true)
        } else {
            ok.add(false)
        }

        if ( password == confirmPassword ) {
            ok.add(true)
        } else {
            ok.add(false)
        }

        return ok
    }


    private fun validSignIn(email: String, password: String, confirmPassword: String): Boolean {
        var ok: ArrayList<Boolean> = validSignInTasks(email, password, confirmPassword)
        var textOk: Boolean = true;
        var i: Int = 0
        while( i < ok.size ) {
            if (!ok[i]) {
                textOk = false
                when (i) {
                    0 -> {
                        showInvalidEmail()
                    }
                    1 -> {
                        showInvalidPassword()
                    }
                    else -> {
                        showPasswordDontMatch()
                    }
                }
                i = ok.size
            }
            i++
        }

        return textOk
    }


    private fun signIn() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        if (validSignIn(email, password, etConfirmPassword.text.toString())) {

            val auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val user = auth.currentUser
                        val db = FirebaseFirestore.getInstance()
                        if (user != null) {
                            db.collection("users").document(user.uid).set(
                                hashMapOf(
                                    "id" to user.uid,
                                    "email" to user.email
                                )
                            )
                            goToHome(user.uid)
                        } else {
                            showRegisterError()
                        }
                    }
                }

        }
    }

    private fun back() {
        navigator.navigate(StartFragment(), false, StartFragment().LOG_TAG, container = R.id.fragmentContainerMain)
    }

    private fun goToHome(uid: String) {
        val intent = Intent(activity?.applicationContext, HomeActivity::class.java).apply {
            putExtra("user", uid)
        }
        writeAuthOn(uid)
        navigator.navigateToActivity(intent, Bundle())
    }

    private fun writeAuthOn(uid: String?) {
        val prefs = activity?.getSharedPreferences(resources.getString(R.string.prefs_file), Context.MODE_PRIVATE)!!.edit()
        prefs.putString("uid", uid)
        prefs.apply()
    }

    private fun showRegisterError() {
        showToast("Error in your register")
    }

    private fun showInvalidEmail() {
        showToast("Invalid email")
    }

    private fun showInvalidPassword() {
        showToast("Invalid password")
    }

    private fun showPasswordDontMatch() {
        showToast("Password don't match")
    }
}
