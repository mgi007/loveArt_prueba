package miguel.insua.loveArt.modules.listMovies


import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_list_movies.*
import miguel.insua.loveArt.R
import miguel.insua.loveArt.databinding.FragmentExampleBinding
import miguel.insua.loveArt.databinding.FragmentHomeBinding
import miguel.insua.loveArt.databinding.FragmentListMoviesBinding
import miguel.insua.loveArt.model.ListDocumentRequest
import miguel.insua.loveArt.model.Media
import miguel.insua.loveArt.model.MediaRequestFirebase
import miguel.insua.loveArt.modules.base.BaseFragment
import miguel.insua.loveArt.modules.home.HomeAdapter
import miguel.insua.loveArt.modules.lists.ListsFragment
import miguel.insua.loveArt.modules.movie.MovieFragment
import kotlin.math.absoluteValue


class ListMoviesFragment : ListMoviesAdapter.ItemOnClickListener, BaseFragment<ListMoviesViewModel, FragmentListMoviesBinding>(
    ListMoviesViewModel::class.java
) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (arguments != null) {
            viewModel.uid = requireArguments().getString("uid")!!
            viewModel.listName = requireArguments().getString("listName")!!
            refreshMoviesFromList(viewModel.uid, viewModel.listName)
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_list_movies
    }

    override fun viewCreated(view: View?) {
        mBinding.viewModel = viewModel
        viewModel.back = :: back
        if (arguments != null) {
            viewModel.uid = requireArguments().getString("uid")!!
            viewModel.listName = requireArguments().getString("listName")!!
            initListMoviesAdapter()
        }
    }

    private fun back() {
        val bundle: Bundle = Bundle()
        bundle.putString("uid", viewModel.uid)
        val fragment = ListsFragment()
        fragment.arguments = bundle
        navigator.navigate(fragment, false, fragment.LOG_TAG, container = R.id.fragmentContainerHome)
    }

    private fun initListMoviesAdapter() {
        val layoutManager = GridLayoutManager(context, 1)
        recycler_view_list_movies.layoutManager = layoutManager
        recycler_view_list_movies.addItemDecoration(
            DividerItemDecoration(
            requireContext().applicationContext,
            layoutManager.orientation)
        )
        recycler_view_list_movies.adapter = ListMoviesAdapter(requireContext().applicationContext, this)
        viewModel.adapter = recycler_view_list_movies.adapter as ListMoviesAdapter
    }

    override fun onItemClick(mediaFirebase: MediaRequestFirebase) {
        val bundle: Bundle = Bundle()
        val media: Media = Media(id = mediaFirebase.id!!.toInt())
        bundle.putParcelable("media", media)
        bundle.putString("uid", viewModel.uid)
        val fragment: MovieFragment = MovieFragment()
        fragment.arguments = bundle
        navigator.navigate(fragment, false, fragment.LOG_TAG, container = R.id.fragmentContainerHome)
    }

    override fun onDeleteClick(media: MediaRequestFirebase) {
        removeMovieFromList(media, viewModel.uid, viewModel.listName)
    }

    private fun getMovies(moviesId: ArrayList<Int>) {
        if (!moviesId.isNullOrEmpty()) {
            viewModel.db.collection("movies").whereIn("id", moviesId).get()
                .addOnSuccessListener {
                    var movies = arrayListOf<MediaRequestFirebase>()
                    for (document in it) {
                        val movieId = document.getLong("id")
                        val tittle = document.getString("tittle")
                        val ogLanguage = document.getString("original_language")
                        val posterPath = document.getString("poster_path")
                        val rating = document.getDouble("vote_average")
                        val voteCount = document.getLong("vote_count")
                        val releaseDate = document.getString("release_date")
                        if (movieId != null) {
                            // Create object with the request
                            val movie = MediaRequestFirebase()
                            movie.id = movieId
                            movie.tittle = tittle
                            movie.og_language = ogLanguage
                            movie.imageUrl = posterPath
                            movie.rating = rating
                            movie.vote_count = voteCount
                            movie.releaseDate = releaseDate
                            movies.add(movie)
                        } else {
                            showToast("ERROR")
                        }
                    }
                    viewModel.refreshData(movies)
                }
        }
    }

    private fun removeMovieFromList(media: MediaRequestFirebase, userId: String, listName: String) {
        viewModel.db.collection("users").document(userId).collection("lists").document(listName).get()
            .addOnSuccessListener {
                val listData = it.toObject(ListDocumentRequest::class.java)
                if (listData?.movies != null) {
                    listData.movies.remove(media.id!!.toInt())
                    // Update data of the list
                    viewModel.db.collection("users").document(userId).collection("lists").document(listName).set(
                        hashMapOf(
                            "movies" to listData.movies
                        )
                    ).addOnSuccessListener {
                        // Save the movie in Firestore
                        refreshMoviesFromList(viewModel.uid, viewModel.listName)
                    }
                }
            }

    }

    private fun refreshMoviesFromList(userId: String, listName: String) {
        // Get data of the list
        viewModel.db.collection("users").document(userId).collection("lists").document(listName).get()
            .addOnSuccessListener {
                val listData = it.toObject(ListDocumentRequest::class.java)
                if (listData?.movies != null) {
                    getMovies(listData.movies)
                }
            }

    }
}
