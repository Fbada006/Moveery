package com.droidafricana.moveery.ui.details.shows

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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.droidafricana.moveery.R
import com.droidafricana.moveery.databinding.ShowDetailsFragmentBinding
import com.droidafricana.moveery.di.Injectable
import com.droidafricana.moveery.models.shows.TvShow
import com.droidafricana.moveery.ui.details.VideoAdapter
import com.droidafricana.moveery.utils.Constants
import com.droidafricana.moveery.utils.Constants.SHOW_TYPE
import com.droidafricana.moveery.utils.DetailsHelper
import com.droidafricana.moveery.utils.OnShowClickListener
import com.droidafricana.moveery.utils.OnVideoClickListener
import com.droidafricana.moveery.utils.Resource
import com.droidafricana.moveery.utils.buildShareIntent
import com.droidafricana.moveery.utils.loadImage
import com.droidafricana.moveery.utils.loadImagesWhenScrollIsPaused
import com.droidafricana.moveery.utils.playVideo
import com.droidafricana.moveery.utils.showAndHandleBackButton
import com.droidafricana.moveery.utils.toPercentage
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

        binding.lifecycleOwner = viewLifecycleOwner
        binding.showDetailsViewModel = viewModel

        val tvShow = args.show

        if (tvShow != null) {
            displayShowDetails(tvShow)
            viewModel.getSimilarShows(tvShow.id)
            viewModel.getVideosResource(tvShow.id)
        }

        val videoAdapter = VideoAdapter(
            requireContext(),
            OnVideoClickListener { playVideo(it) }
        )

        val adapter = ShowSimilarPagedAdapter(requireContext(), OnShowClickListener {
            viewModel.displayShowDetails(it!!)
        })

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

        //Observe the navigation event as well using the convenient Event class
        viewModel.navigateToSelectedShow.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { tvShow ->
                findNavController().navigate(
                    ShowDetailsFragmentDirections.actionDestShowDetailsFragmentToDestSimilarShowFragment(
                        tvShow
                    )
                )
            }
        })

        tvShow?.id.let {
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
    }

    private fun onShareFabClicked() {
        binding.shareShow.setOnClickListener { buildShareIntent(args.show!!.id, SHOW_TYPE) }
    }

    private fun observeLikedState() {
        viewModel.isShowInFav(args.show?.id!!).observe(viewLifecycleOwner, Observer {
            binding.likeButton.isLiked = it != null
        })
    }

    private fun onLikeButtonClicked() {
        binding.likeButton.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                args.show?.let { viewModel.insertShowIntoFav(it) }
            }

            override fun unLiked(likeButton: LikeButton?) {
                args.show?.let { viewModel.deleteShowFromFav(it.id) }
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