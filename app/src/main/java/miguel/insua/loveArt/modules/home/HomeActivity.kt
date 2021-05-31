package miguel.insua.loveArt.modules.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.toolbar.*
import miguel.insua.loveArt.R
import miguel.insua.loveArt.databinding.ActivityHomeBinding
import miguel.insua.loveArt.modules.base.BaseActivity
import miguel.insua.loveArt.modules.lists.ListsFragment
import miguel.insua.loveArt.modules.main.MainActivity

class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>(
    HomeViewModel::class.java
) {

    lateinit var uid: String

    var toolbarInstance: Toolbar = Toolbar()

    override fun getLayoutRes(): Int {
        return R.layout.activity_home
    }

    override fun initViewModel(viewModel: HomeViewModel) {
        binding.viewModel = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uid = intent.extras?.getString("uid").toString()
        if (viewModel.fragmentState == FragmentState.HOME) {
            navigator.addFragment(HomeFragment(), R.id.fragmentContainerHome)
            toolbarInstance.show(this, title = resources.getString(R.string.home), false)
        } else if (viewModel.fragmentState == FragmentState.LISTS) {
            navigator.addFragment(ListsFragment(), R.id.fragmentContainerHome)
            toolbarInstance.show(this, title = resources.getString(R.string.my_lists), false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        when(viewModel.fragmentState) {
            FragmentState.HOME -> {
                menuInflater.inflate(R.menu.home_menu, menu)
            }
            FragmentState.LISTS -> {
                menuInflater.inflate(R.menu.lists_menu, menu)
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.my_lists) {
            goTo(FragmentState.LISTS)
        }

        if (item.itemId == R.id.go_to_home) {
            goTo(FragmentState.HOME)
        }

        if (item.itemId == R.id.log_out) {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, MainActivity::class.java)
            writeAuthOff()
            navigator.navigateToActivity(intent, Bundle())
        }

        return super.onOptionsItemSelected(item)
    }


    private fun goTo(fragmentToGo: FragmentState?) {

        when (viewModel.fragmentState) {
            FragmentState.HOME -> {
                toolbar.menu.removeItem(R.id.my_lists)
                toolbar.menu.removeItem(R.id.log_out)
            }
            FragmentState.LISTS -> {
                toolbar.menu.removeItem(R.id.go_to_home)
                toolbar.menu.removeItem(R.id.log_out)
            }
        }

        when (fragmentToGo) {
            FragmentState.HOME -> {
                menuInflater.inflate(R.menu.home_menu, toolbar.menu)
                navigator.navigate(HomeFragment(), false, HomeFragment().LOG_TAG, container = R.id.fragmentContainerHome)
                viewModel.fragmentState = FragmentState.HOME
                toolbarInstance.show(this, title = resources.getString(R.string.home), false)
            }
            FragmentState.LISTS -> {
                menuInflater.inflate(R.menu.lists_menu, toolbar.menu)
                navigator.navigate(ListsFragment(), false, ListsFragment().LOG_TAG, container = R.id.fragmentContainerHome)
                viewModel.fragmentState = FragmentState.LISTS
                toolbarInstance.show(this, title = resources.getString(R.string.my_lists), false)
            }
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        goTo(null)
    }


    private fun writeAuthOff() {
        val prefs = getSharedPreferences(resources.getString(R.string.prefs_file), Context.MODE_PRIVATE)!!.edit()
        prefs.clear()
        prefs.apply()
    }
}
