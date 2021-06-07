package miguel.insua.loveArt.modules.movie.moviePager


import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_movie_pager.*
import miguel.insua.loveArt.R
import miguel.insua.loveArt.databinding.FragmentExampleBinding
import miguel.insua.loveArt.databinding.FragmentHomeBinding
import miguel.insua.loveArt.databinding.FragmentMoviePagerBinding
import miguel.insua.loveArt.model.Media
import miguel.insua.loveArt.modules.base.BaseFragment
import miguel.insua.loveArt.modules.home.HomeAdapter
import miguel.insua.loveArt.modules.movie.MovieFragment


class MoviePagerFragment(private val itemClickListener: MoviePagerClickListener) : HomeAdapter.ItemOnClickListener, BaseFragment<MoviePagerViewModel, FragmentMoviePagerBinding>(
    MoviePagerViewModel::class.java
) {

    interface MoviePagerClickListener {
        fun onClickItemMoviePager(media: Media)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_movie_pager
    }

    override fun viewCreated(view: View?) {
        mBinding.viewModel = viewModel
        initHomeAdapter()
    }

    private fun initHomeAdapter() {
        val layoutManager = GridLayoutManager(context, 1)
        recycler_view_movie_extensions.layoutManager = layoutManager
        recycler_view_movie_extensions.addItemDecoration(
            DividerItemDecoration(
            requireContext().applicationContext,
            layoutManager.orientation)
        )
        recycler_view_movie_extensions.adapter = HomeAdapter(requireContext().applicationContext, this)
        viewModel.adapter = recycler_view_movie_extensions.adapter as HomeAdapter
    }

    override fun onItemClick(media: Media) {
        itemClickListener.onClickItemMoviePager(media)
    }

}
