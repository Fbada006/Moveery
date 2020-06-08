package com.disruption.moveery.ui.details.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.disruption.moveery.R
import com.disruption.moveery.databinding.ShowDetailsFragmentBinding
import com.disruption.moveery.di.Injectable
import com.disruption.moveery.models.shows.TvShow
import com.disruption.moveery.ui.details.VideoAdapter
import com.disruption.moveery.utils.*
import com.disruption.moveery.utils.Constants.SHOW_TYPE
import com.like.LikeButton
import com.like.OnLikeListener
import javax.inject.Inject

/**Fragment to show details of a clicked [TvShow]*/
class ShowDetailsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var requestManager: RequestManager

    private val args: ShowDetailsFragmentArgs by navArgs()

    companion object {
        fun newInstance() = ShowDetailsFragment()
    }

    private val viewModel by viewModels<ShowDetailsViewModel> { viewModelFactory }
    private lateinit var binding: ShowDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.show_details_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.showAndHandleBackButton(activity)

        val tvShow = args.tvshow

        if (tvShow != null) {
            displayShowDetails(tvShow)
            viewModel.getSimilarShows(tvShow.id)
            viewModel.getVideosResource(tvShow.id)
        }

        val videoAdapter = VideoAdapter(
            requireContext(),
            OnVideoClickListener { playVideo(it) }
        )

        val adapter = ShowSimilarPagedAdapter(requireContext())

        binding.similarShowsList.adapter = adapter
        binding.videoShowsList.adapter = videoAdapter

        val similarLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val videoLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.similarShowsList.layoutManager = similarLayoutManager
        binding.videoShowsList.layoutManager = videoLayoutManager

        viewModel.showList.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) binding.similarShowsError.visibility = View.VISIBLE else View.GONE
            adapter.submitList(it)
        })

        tvShow?.id?.let {
            viewModel.videoRes.observe(viewLifecycleOwner, Observer { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        if (!resource.data.isNullOrEmpty()) {
                            videoAdapter.submitList(resource.data)
                        } else {
                            showError()
                        }
                    }
                    Resource.Status.ERROR -> showError()
                    Resource.Status.LOADING -> showLoading()
                }
            })
        }

        //Listen to the scrolls appropriately for efficient loading with user data in mind
        loadImagesWhenScrollIsPaused(binding.similarShowsList)

        observeLikedState()
        onLikeButtonClicked()
        onShareFabClicked()

        binding.showDetailsViewModel = viewModel
    }

    private fun onShareFabClicked() {
        binding.shareMovie.setOnClickListener { buildShareIntent(args.tvshow!!.id, SHOW_TYPE) }
    }

    private fun observeLikedState() {
        viewModel.isShowInFav(args.tvshow?.id!!).observe(viewLifecycleOwner, Observer {
            binding.likeButton.isLiked = it != null
        })
    }

    private fun onLikeButtonClicked() {
        binding.likeButton.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                args.tvshow?.let { viewModel.insertShowIntoFav(it) }
            }

            override fun unLiked(likeButton: LikeButton?) {
                args.tvshow?.let { viewModel.deleteShowFromFav(it.id) }
            }
        })
    }

    /**Display the passed in movie from the [args]*/
    private fun displayShowDetails(tvShow: TvShow?) {
        val posterUrl = Constants.IMAGE_BASE_URL + tvShow?.poster_path

        binding.ivShowPoster.loadImage(posterUrl, requestManager)

        binding.tvShowTitle.text = tvShow?.name
        binding.tvShowGenre.text = DetailsHelper.getGenres(tvShow?.genre_ids, requireContext())
        binding.tvShowYear.text = tvShow?.first_air_date?.substring(0, 4)
        binding.tvShowOverview.text = tvShow?.overview
        binding.tvShowRating.text = tvShow?.vote_average?.toPercentage()
        binding.ratingCustomView.apply {
            setValue(tvShow?.vote_average?.toInt()!!.times(10))
            setFillColor(
                DetailsHelper.getRatingColor(
                    tvShow.vote_average.toInt().times(10),
                    requireContext()
                )
            )
            setStrokeColor(ContextCompat.getColor(requireContext(), R.color.colorAccent))
        }
    }

    private fun showLoading() {
        binding.videoLoadingSpinner.visibility = View.VISIBLE
        binding.videoMoviesError.visibility = View.GONE
    }

    private fun showError() {
        binding.videoLoadingSpinner.visibility = View.GONE
        binding.videoMoviesError.visibility = View.VISIBLE
    }
}