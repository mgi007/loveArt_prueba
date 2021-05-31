package miguel.insua.loveArt.modules.start


import android.view.View
import miguel.insua.loveArt.R
import miguel.insua.loveArt.databinding.FragmentStartBinding
import miguel.insua.loveArt.modules.base.BaseFragment
import miguel.insua.loveArt.modules.login.LoginFragment
import miguel.insua.loveArt.modules.siEmail.SiEmailFragment


class StartFragment : BaseFragment<StartViewModel, FragmentStartBinding>(
    StartViewModel::class.java
) {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_start
    }

    override fun viewCreated(view: View?) {
        mBinding.viewModel = viewModel
        viewModel.logIn = ::logIn
        viewModel.email = ::email
    }

    private fun logIn() {
        navigator.navigate(LoginFragment(), false, LoginFragment().LOG_TAG, container = R.id.fragmentContainerMain)
    }

    private fun email() {
        navigator.navigate(SiEmailFragment(), false, SiEmailFragment().LOG_TAG, container = R.id.fragmentContainerMain)
    }

}
