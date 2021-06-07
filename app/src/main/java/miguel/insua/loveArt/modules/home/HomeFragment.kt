package miguel.insua.loveArt.modules.home



import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import miguel.insua.loveArt.R
import miguel.insua.loveArt.api.TMDbApiManager
import miguel.insua.loveArt.api.TMDbService
import miguel.insua.loveArt.databinding.FragmentHomeBinding
import miguel.insua.loveArt.model.Media
import miguel.insua.loveArt.model.MediaResponse
import miguel.insua.loveArt.modules.base.BaseFragment
import miguel.insua.loveArt.modules.movie.MovieFragment
import retrofit2.Response

class HomeFragment : HomeAdapter.ItemOnClickListener, BaseFragment<HomeViewModel, FragmentHomeBinding>(
    HomeViewModel::class.java
) {

    lateinit var uid: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getMovies("popular", 1)

        if (arguments != null) {
            uid = requireArguments().getString("uid")!!
        }

    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }

    override fun viewCreated(view: View?) {
        mBinding.viewModel = viewModel
        val spinnerList = resources.getStringArray(R.array.select_movie_query)
        val spinnerAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            requireContext().applicationContext,
            R.array.select_movie_query,
            android.R.layout.simple_spinner_item
        )
        select_movie_query.adapter = spinnerAdapter
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        select_movie_query.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectMovieQuery(spinnerList[position], 1)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
        }
        initHomeAdapter()
    }

    private fun initHomeAdapter() {
        val layoutManager = GridLayoutManager(context, 1)
        recycler_view.layoutManager = layoutManager
        recycler_view.addItemDecoration(DividerItemDecoration(
            requireContext().applicationContext,
            layoutManager.orientation)
        )
        recycler_view.adapter = HomeAdapter(requireContext().applicationContext, this)
        viewModel.adapter = recycler_view.adapter as HomeAdapter
    }

    private fun getMovies(query: String, numPage: Int) {
        val completeQuery: String = "$query?api_key=5451f06f86322e090841b4c2ebab2b7d&page=$numPage"
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<MediaResponse> =
                TMDbApiManager().getRetrofit().create(TMDbService::class.java).getMovies("$completeQuery")
            val mediaResponse: MediaResponse? = call.body()
            activity?.runOnUiThread(Runnable {
                if (call.isSuccessful) {
                    // Show in RecyclerView
                    val movies: List<Media>? = mediaResponse?.results
                    viewModel.list.clear()
                    if (movies != null) {
                        viewModel.list.addAll(movies)
                    }
                    viewModel.refreshData()



                } else {
                    showToast(R.string.error_loading_popular.toString())
                }
            })
        }
    }

    private fun selectMovieQuery(searchOption: String, numPage: Int) {
        val optionsArray = resources.getStringArray(R.array.select_movie_query)
        when (searchOption) {
            optionsArray[0] -> {
                getMovies("popular", numPage)
            }
            optionsArray[1] -> {
                getMovies("now_playing", numPage)
            }
            optionsArray[2] -> {
                getMovies("top_rated", numPage)
            }
            optionsArray[3] -> {
                getMovies("upcoming", numPage)
            }
        }
    }

    override fun onItemClick(media: Media) {
        val bundle: Bundle = Bundle()
        bundle.putParcelable("media", media)
        bundle.putString("uid", uid)
        val fragment: MovieFragment = MovieFragment()
        fragment.arguments = bundle
        navigator.navigate(fragment, false, fragment.LOG_TAG, container = R.id.fragmentContainerHome)
    }

}
