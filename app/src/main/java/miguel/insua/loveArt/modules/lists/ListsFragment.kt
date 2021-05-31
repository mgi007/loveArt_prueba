package miguel.insua.loveArt.modules.lists


import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.ListFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_lists.*
import kotlinx.android.synthetic.main.input_name_list.view.*
import miguel.insua.loveArt.R
import miguel.insua.loveArt.databinding.FragmentExampleBinding
import miguel.insua.loveArt.databinding.FragmentHomeBinding
import miguel.insua.loveArt.databinding.FragmentListsBinding
import miguel.insua.loveArt.modules.base.BaseFragment
import miguel.insua.loveArt.modules.home.HomeAdapter


class ListsFragment : ListAdapter.ListItemOnClickListener,BaseFragment<ListsViewModel, FragmentListsBinding>(
    ListsViewModel::class.java
) {

    private lateinit var adapter: ListAdapter

    override fun getLayoutRes(): Int {
        return R.layout.fragment_lists
    }

    override fun viewCreated(view: View?) {
        mBinding.viewModel = viewModel
        viewModel.newList = ::newList
        initListsAdapter()
    }

    private fun initListsAdapter() {
        val layoutManager = GridLayoutManager(context, 1)
        recycler_view_lists.layoutManager = layoutManager
        val appContext = requireContext().applicationContext
        adapter = ListAdapter(appContext, this)
        recycler_view_lists.adapter = adapter
        observeData()
    }

    override fun onEditClick(nameList: String) {
        showToast(nameList)
    }
    override fun onDeleteClick(nameList: String) {
        showToast(nameList)
        viewModel.deleteList(nameList)
        observeData()
    }

    private fun newList() {
        var listName: String = ""
        val inputDialogView = LayoutInflater.from(context).inflate(R.layout.input_name_list, null)
        val windowBuilder = AlertDialog.Builder(context)
            .setView(inputDialogView)
            .setTitle(R.string.new_list)
        val windowAlertDialog = windowBuilder.show()
        inputDialogView.btn_create_list.setOnClickListener {
            if (inputDialogView.input_list_name.text.toString() != "") {
                listName = inputDialogView.input_list_name.text.toString()
                windowAlertDialog.dismiss()
                viewModel.newList(listName)
                observeData()
            }
        }
    }


    private fun observeData() {
        viewModel.refreshData().observe(this, Observer {
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
        })
    }
}
