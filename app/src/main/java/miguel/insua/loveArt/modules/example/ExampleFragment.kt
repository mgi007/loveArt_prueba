package miguel.insua.loveArt.modules.example


import android.view.View
import miguel.insua.loveArt.R
import miguel.insua.loveArt.databinding.FragmentExampleBinding
import miguel.insua.loveArt.databinding.FragmentHomeBinding
import miguel.insua.loveArt.modules.base.BaseFragment


class ExampleFragment : BaseFragment<ExampleViewModel, FragmentExampleBinding>(
    ExampleViewModel::class.java
) {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_example
    }

    override fun viewCreated(view: View?) {
        mBinding.viewModel = viewModel
    }
}
