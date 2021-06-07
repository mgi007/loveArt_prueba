package miguel.insua.loveArt.modules.home



import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import miguel.insua.loveArt.modules.utils.setVisible
import retrofit2.Response

class HomeFragment : HomeAdapter.ItemOnClickListener, BaseFragment<HomeViewModel, FragmentHomeBinding>(
    HomeViewModel::class.java
) {

    lateinit var uid: String

    private var numPage: Int = 1

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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
                    selectMovieQuery(spinnerList[position])
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

    override fun getPage() {
        home_progress_bar.setVisible(true)
        numPage++
        getMoviesRequest(numPage, false)
    }

    private fun getMovies() {
        numPage = 1
        getMoviesRequest(numPage, true)
    }

    private fun getMoviesRequest(pageNumber: Int, queryChanges: Boolean) {
        val completeQuery: String = "${viewModel.searchOption}?api_key=5451f06f86322e090841b4c2ebab2b7d&page=$pageNumber"
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<MediaResponse> =
                TMDbApiManager().getRetrofit().create(TMDbService::class.java).getMovies("$completeQuery")
            val mediaResponse: MediaResponse? = call.body()
            activity?.runOnUiThread(Runnable {
                if (call.isSuccessful) {
                    // Show in RecyclerView
                    val movies: List<Media>? = mediaResponse?.results
                    if (queryChanges) {
                        viewModel.list.clear()
                    } else {
                        home_progress_bar.setVisible(false)
                    }
                    if (movies != null) {
                        viewModel.list.addAll(movies)
                    }
                    viewModel.refreshData()


                } else {
                    showToast(R.string.error_loading_popular.toString())
                    if (!queryChanges) {
                        home_progress_bar.setVisible(false)
                    }
                }
            })
        }
    }

    private fun selectMovieQuery(searchOption: String) {
        val optionsArray = resources.getStringArray(R.array.select_movie_query)
        when (searchOption) {
            optionsArray[0] -> {
                viewModel.searchOption = "popular"
            }
            optionsArray[1] -> {
                viewModel.searchOption = "now_playing"
            }
            optionsArray[2] -> {
                viewModel.searchOption = "top_rated"
            }
            optionsArray[3] -> {
                viewModel.searchOption = "top_rated"
            }
        }
        getMovies()
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
