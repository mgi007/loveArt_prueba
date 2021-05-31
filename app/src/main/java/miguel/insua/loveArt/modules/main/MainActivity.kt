package miguel.insua.loveArt.modules.main


import android.content.Context
import android.content.Intent
import android.os.Bundle
import miguel.insua.loveArt.R
import miguel.insua.loveArt.databinding.ActivityMainBinding
import miguel.insua.loveArt.modules.base.BaseActivity
import miguel.insua.loveArt.modules.home.HomeActivity
import miguel.insua.loveArt.modules.start.StartFragment


class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(
    MainViewModel::class.java
) {
    override fun initViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun getLayoutRes() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uid: String? = isSessionActive()
        if (uid == null) {
            navigator.addFragment(StartFragment(), container = R.id.fragmentContainerMain)
        } else {
            val intent = Intent(this, HomeActivity::class.java).apply {
                putExtra("user", uid)
            }
            navigator.navigateToActivity(intent, Bundle())
        }
    }

    private fun isSessionActive(): String? {
        val prefs =
            getSharedPreferences(resources.getString(R.string.prefs_file), Context.MODE_PRIVATE)
        return prefs.getString("uid", null)
    }

}
