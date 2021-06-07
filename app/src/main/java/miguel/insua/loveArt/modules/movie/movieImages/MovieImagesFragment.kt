package miguel.insua.loveArt.modules.movie.movieImages


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_movie_images.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import miguel.insua.loveArt.R
import miguel.insua.loveArt.api.TMDbApiManager
import miguel.insua.loveArt.api.TMDbService
import miguel.insua.loveArt.databinding.FragmentMovieImagesBinding
import miguel.insua.loveArt.model.Media
import miguel.insua.loveArt.model.MovieImages
import miguel.insua.loveArt.model.MovieImagesCollection
import miguel.insua.loveArt.modules.base.BaseFragment
import miguel.insua.loveArt.modules.home.HomeFragment
import miguel.insua.loveArt.modules.movie.MovieFragment
import retrofit2.Response


class MovieImagesFragment : BaseFragment<MovieImagesViewModel, FragmentMovieImagesBinding>(
    MovieImagesViewModel::class.java
) {

    var movieImages: MovieImages? = null

    private lateinit var adapterBackdrops: MovieImagesAdapter

    private lateinit var adapterPosters: MovieImagesAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (arguments != null) {
            viewModel.media = requireArguments().getParcelable<Media>("media")!!
            getMovieImages(viewModel.media.id.toString())
            viewModel.uid = requireArguments().getString("uid")!!
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_movie_images
    }

    override fun viewCreated(view: View?) {
        mBinding.viewModel = viewModel
        viewModel.back = ::back
        initBackdropsAdapter()
        initPostersAdapter()
    }

    private fun back() {
        val bundle: Bundle = Bundle()
        bundle.putParcelable("media", viewModel.media)
        bundle.putString("uid", viewModel.uid)
        val fragment: MovieFragment = MovieFragment()
        fragment.arguments = bundle
        navigator.navigate(fragment, false, fragment.LOG_TAG, container = R.id.fragmentContainerHome)
    }

    private fun getMovieImages(id: String) {
        val completeQuery: String = "$id/images?api_key=5451f06f86322e090841b4c2ebab2b7d"
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<MovieImages> = TMDbApiManager().getRetrofit().create(TMDbService::class.java)
                .getMovieImages("$completeQuery")
            val movieImagesResponse: MovieImages? = call.body()
            activity?.runOnUiThread(Runnable {
                if (call.isSuccessful) {
                    if (movieImagesResponse != null) {
                        movieImages = movieImagesResponse
                        refreshMovieImages(movieImages!!)
                    }
                }
            })
        }
    }

    private fun initBackdropsAdapter() {
        val layoutManager = GridLayoutManager(context, 2)
        recycler_view_movie_backdrops.layoutManager = layoutManager
        val appContext = requireContext().applicationContext
        adapterBackdrops = MovieImagesAdapter(appContext)
        recycler_view_movie_backdrops.adapter = adapterBackdrops
    }

    private fun initPostersAdapter() {
        val layoutManager = GridLayoutManager(context, 2)
        recycler_view_movie_posters.layoutManager = layoutManager
        val appContext = requireContext().applicationContext
        adapterPosters = MovieImagesAdapter(appContext)
        recycler_view_movie_posters.adapter = adapterPosters
    }

    private fun refreshMovieImages(newMovieImages: MovieImages) {
        if (newMovieImages != null) {
            val backdropsList = mutableListOf<MovieImagesCollection>()
            if (newMovieImages.backdrops != null) {
                backdropsList.addAll(movieImages!!.backdrops!!)
                adapterBackdrops.setListData(backdropsList)
                adapterBackdrops.notifyDataSetChanged()
            }
            val postersList = mutableListOf<MovieImagesCollection>()
            if (newMovieImages.posters != null) {
                postersList.addAll(newMovieImages.posters)
                adapterPosters.setListData(postersList)
                adapterPosters.notifyDataSetChanged()
            }
        }
    }

}
