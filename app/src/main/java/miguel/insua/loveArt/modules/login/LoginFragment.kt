package miguel.insua.loveArt.modules.login


import Valid
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import miguel.insua.loveArt.R
import miguel.insua.loveArt.api.FirebaseApiManager
import miguel.insua.loveArt.databinding.FragmentLoginBinding
import miguel.insua.loveArt.modules.base.BaseFragment
import miguel.insua.loveArt.modules.home.HomeActivity
import miguel.insua.loveArt.modules.start.StartFragment


class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>(
    LoginViewModel::class.java
) {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_login
    }

    override fun viewCreated(view: View?) {
        mBinding.viewModel = viewModel
        viewModel.logIn = ::logIn
        viewModel.back = ::back
    }


    private fun logIn () {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        val valid = validLogIn()
        if (valid) {
            val auth = FirebaseAuth.getInstance()
            auth.signInWithEmailAndPassword(
                email,
                password
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    goToHome(auth.currentUser!!.uid)
                } else {
                    showLoginError()
                }
            }
        }
    }

    private fun back() {
        navigator.navigate(StartFragment(), false, StartFragment().LOG_TAG, container = R.id.fragmentContainerMain)
    }

    private fun goToHome(uid: String) {
        val intent = Intent(activity?.applicationContext, HomeActivity::class.java)
        val bundle = Bundle()
        bundle.putString("uid", uid)
        intent.putExtras(bundle)
        writeAuthOn(uid)
        navigator.navigateToActivity(intent, Bundle())
    }

    private fun validLogIn(): Boolean {
        var ok: Boolean = false
        if ( Valid.isEmailValid(etEmail.text.toString()) ) {
            if ( Valid.isPasswordValid(etPassword.text.toString()) ) {
                ok = true
            } else {
                showInvalidPassword()
            }
        } else {
            showInvalidEmail()
        }
        return ok
    }

    private fun writeAuthOn(uid: String?) {
        val prefs = activity?.getSharedPreferences(resources.getString(R.string.prefs_file), Context.MODE_PRIVATE)!!.edit()
        prefs.putString("uid", uid)
        prefs.apply()
    }


    private fun showLoginError() {
        showToast("Error in your log in")
    }

    private fun showInvalidEmail() {
        showToast("Invalid email")
    }

    private fun showInvalidPassword() {
        showToast("Invalid password")
    }

}
