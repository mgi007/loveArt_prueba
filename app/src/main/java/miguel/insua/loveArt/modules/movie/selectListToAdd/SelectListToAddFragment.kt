package miguel.insua.loveArt.modules.movie.selectListToAdd


import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_lists.*
import kotlinx.android.synthetic.main.fragment_select_list_to_add.*
import miguel.insua.loveArt.R
import miguel.insua.loveArt.api.FirebaseMapper
import miguel.insua.loveArt.databinding.FragmentExampleBinding
import miguel.insua.loveArt.databinding.FragmentHomeBinding
import miguel.insua.loveArt.databinding.FragmentSelectListToAddBinding
import miguel.insua.loveArt.model.ListDocumentRequest
import miguel.insua.loveArt.model.Media
import miguel.insua.loveArt.model.MovieImages
import miguel.insua.loveArt.modules.base.BaseFragment
import miguel.insua.loveArt.modules.lists.ListAdapter
import miguel.insua.loveArt.modules.movie.MovieFragment


class SelectListToAddFragment : SelectListAdapter.ListItemOnClickListener ,BaseFragment<SelectListToAddViewModel, FragmentSelectListToAddBinding>(
    SelectListToAddViewModel::class.java
) {

    lateinit var media: Media

    lateinit var uid: String

    private lateinit var adapter: SelectListAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (arguments != null) {
            media = requireArguments().getParcelable<Media>("media")!!
            uid = requireArguments().getString("uid")!!
            refreshLists()
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_select_list_to_add
    }

    override fun viewCreated(view: View?) {
        mBinding.viewModel = viewModel
        viewModel.back = ::back
        initSelectListsAdapter()
    }

    private fun back() {
        val bundle: Bundle = Bundle()
        bundle.putParcelable("media", media)
        bundle.putString("uid", uid)
        val fragment: MovieFragment = MovieFragment()
        fragment.arguments = bundle
        navigator.navigate(fragment, false, fragment.LOG_TAG, container = R.id.fragmentContainerHome)
    }

    private fun initSelectListsAdapter() {
        val layoutManager = GridLayoutManager(context, 1)
        recycler_view_select_lists.layoutManager = layoutManager
        val appContext = requireContext().applicationContext
        adapter = SelectListAdapter(appContext, this)
        recycler_view_select_lists.adapter = adapter
    }

    private fun refreshLists() {
        viewModel.db.collection("users").document(uid).collection("lists").get()
            .addOnSuccessListener { result ->
                var listsToAdd: ArrayList<String> = arrayListOf<String>()
                for (document in result) {
                    var add: Boolean = true
                    val listData = document.toObject(ListDocumentRequest::class.java)
                    if (listData == null) {
                        showToast("ERROR")
                    } else {
                        if (listData.movies != null) {
                            if (listData.movies.size > 0 && listData.movies != null) {
                                for (i in 0 until listData.movies.size) {
                                    if (listData.movies[i] == media.id) {
                                        add = false
                                    }
                                }
                            }
                            if (add) {
                                listsToAdd.add(document.id)
                            }
                        }
                    }
                }
                refreshListData(listsToAdd)
            }
    }

    private fun addMovieToList(userId: String, listName: String, movieId: Int) {
        // Get data of the list
        viewModel.db.collection("users").document(userId).collection("lists").document(listName).get()
            .addOnSuccessListener {
                val listData = it.toObject(ListDocumentRequest::class.java)
                if (listData?.movies != null) {
                    listData.movies.add(movieId)
                    // Update data of the list
                    viewModel.db.collection("users").document(userId).collection("lists").document(listName).set(
                        hashMapOf(
                            "movies" to listData.movies
                        )
                    ).addOnSuccessListener {
                        // Save the movie in Firestore
                        saveMovieInBD()
                        back()
                    }
                }
            }

    }

    private fun saveMovieInBD() {
        viewModel.db.collection("movies").document(media.id.toString()).set(FirebaseMapper.getMediaRequestMapper(media))
            .addOnSuccessListener {

            }
    }

    private fun refreshListData(lists: ArrayList<String>) {
        val mutableList = mutableListOf<String>()
        if (lists.size > 0 && lists != null) {
            mutableList.addAll(lists)
        }
        adapter.setListData(mutableList)
        adapter.notifyDataSetChanged()
    }

    override fun onItemClick(listName: String){
        addMovieToList(uid, listName, media.id!!)
    }
}
