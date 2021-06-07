package miguel.insua.loveArt.modules.lists


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_lists.*
import kotlinx.android.synthetic.main.input_name_list.view.*
import miguel.insua.loveArt.R
import miguel.insua.loveArt.api.FirebaseMapper
import miguel.insua.loveArt.databinding.FragmentListsBinding
import miguel.insua.loveArt.model.ListDocumentRequest
import miguel.insua.loveArt.modules.base.BaseFragment
import miguel.insua.loveArt.modules.listMovies.ListMoviesFragment


class ListsFragment : ListAdapter.ListItemOnClickListener,BaseFragment<ListsViewModel, FragmentListsBinding>(
    ListsViewModel::class.java
) {

    private lateinit var adapter: ListAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (arguments != null) {
            viewModel.uid = requireArguments().getString("uid")!!
            viewModel.getUserById(viewModel.uid)
            initListsAdapter()
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_lists
    }

    override fun viewCreated(view: View?) {
        mBinding.viewModel = viewModel
        viewModel.newList = ::newList
    }

    private fun initListsAdapter() {
        val layoutManager = GridLayoutManager(context, 1)
        recycler_view_lists.layoutManager = layoutManager
        val appContext = requireContext().applicationContext
        adapter = ListAdapter(appContext, this)
        recycler_view_lists.adapter = adapter
        refreshListData()
    }

    override fun onEditClick(oldName: String){
        var newName: String = ""
        val inputDialogView = LayoutInflater.from(context).inflate(R.layout.input_name_list, null)
        val windowBuilder = AlertDialog.Builder(context)
            .setView(inputDialogView)
            .setTitle(R.string.new_list_name)
        val windowAlertDialog = windowBuilder.show()
        inputDialogView.btn_create_list.setOnClickListener {
            if (inputDialogView.input_list_name.text.toString() != "") {
                newName = inputDialogView.input_list_name.text.toString()
                viewModel.editListName(oldName, newName)
                editUserDataList()
                refreshListData()
                editListDocumentRequest(oldName, newName)
                windowAlertDialog.dismiss()
            }
        }
    }
    override fun onDeleteClick(listName: String) {
        viewModel.deleteList(listName)
        editUserDataList()
        removeListDocumentRequest(listName)
        refreshListData()
    }

    override fun onItemClick(listName: String) {
        val bundle: Bundle = Bundle()
        bundle.putString("listName", listName)
        bundle.putString("uid", viewModel.uid)
        val fragment = ListMoviesFragment()
        fragment.arguments = bundle
        navigator.navigate(fragment, false, fragment.LOG_TAG, container = R.id.fragmentContainerHome)
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

                viewModel.addList(listName)
                editUserDataList()
                newListDocumentRequest(listName, null)

                refreshListData()
            }
        }
    }

    private fun editUserDataList() {
        if (viewModel.uid != null) {
            if (viewModel.userData != null) {
                viewModel.db.collection("users").document(viewModel.uid)
                    .set(FirebaseMapper.registerRequestMapper(viewModel.userData))
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            showToast("OK")

                        } else {
                            showToast("ERROR")
                        }
                    }
            }
        }
    }

    private fun newListDocumentRequest(listName: String, arrayMovies: ArrayList<Int>?) {
        var movies = arrayListOf<Int>()
        if (arrayMovies != null) {
            var movies = arrayMovies
        }
        if (viewModel.uid != null) {
            viewModel.db.collection("users").document(viewModel.uid)
                .collection("lists").document(listName)
                .set(
                    hashMapOf(
                        "movies" to movies
                    )
                )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showToast("ok")
                    } else {
                        showToast("error")
                    }
                }
        }

    }

    private fun removeListDocumentRequest(listName: String) {
        var movies: ArrayList<Int> = arrayListOf<Int>()
        if (viewModel.uid != null) {
            viewModel.db.collection("users").document(viewModel.uid)
                .collection("lists").document(listName)
                .delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showToast("ok")
                    } else {
                        showToast("error")
                    }
                }
        }

    }


    private fun editListDocumentRequest(oldName: String, newName: String) {
        var movies: ArrayList<Int> = arrayListOf<Int>()
        if (viewModel.uid != null) {
            viewModel.db.collection("users").document(viewModel.uid)
                .collection("lists").document(oldName)
                .get()
                .addOnCompleteListener {
                    val result = it.result
                    if (result != null) {
                        val userRegister = result.toObject(ListDocumentRequest::class.java)
                        if (userRegister != null) {
                            newListDocumentRequest(newName, userRegister.movies)
                            removeListDocumentRequest(oldName)
                            showToast("ok")
                        }
                    }
                }
        }

    }

    private fun observeData() {
        viewModel.refreshData().observe(this, Observer {
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun refreshListData() {
        viewModel.refreshUserLists(viewModel.uid, adapter)
    }
}
