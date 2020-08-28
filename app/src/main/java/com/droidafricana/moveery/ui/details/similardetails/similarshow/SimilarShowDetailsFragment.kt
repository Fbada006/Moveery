package com.droidafricana.moveery.ui.details.similardetails.similarshow

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
import com.droidafricana.moveery.R
import com.droidafricana.moveery.databinding.FragmentSimilarShowDetailsBinding
import com.droidafricana.moveery.di.Injectable
import com.droidafricana.moveery.models.shows.TvShow
import com.droidafricana.moveery.ui.details.VideoAdapter
import com.droidafricana.moveery.utils.Constants
import com.droidafricana.moveery.utils.DetailsHelper
import com.droidafricana.moveery.utils.OnVideoClickListener
import com.droidafricana.moveery.utils.Resource
import com.droidafricana.moveery.utils.buildShareIntent
import com.droidafricana.moveery.utils.loadImage
import com.droidafricana.moveery.utils.playVideo
import com.droidafricana.moveery.utils.showAndHandleBackButton
import com.droidafricana.moveery.utils.toPercentage
import com.like.LikeButton
import com.like.OnLikeListener
import javax.inject.Inject

/**
 *Display similar movie details excluding the "Similar" section
 */
class SimilarShowDetailsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var requestManager: RequestManager
    private lateinit var binding: FragmentSimilarShowDetailsBinding
    private val viewModel: SimilarShowsViewModel by viewModels { viewModelFactory }
    private val args: SimilarShowDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_similar_show_details, container, false
        )

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.toolbar.showAndHandleBackButton(activity)

        binding.lifecycleOwner = viewLifecycleOwner

        val tvShow = args.tvShow

        if (tvShow != null) {
            displayShowDetails(tvShow)
            viewModel.getVideosResource(tvShow.id)
        }

        val videoAdapter = VideoAdapter(
            requireContext(),
            OnVideoClickListener { playVideo(it) }
        )

        binding.similarVideoShowsList.adapter = videoAdapter

        val videoLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.similarVideoShowsList.layoutManager = videoLayoutManager

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

        observeLikedState()
        onLikeButtonClicked()
        onShareFabClicked()
    }

    private fun onShareFabClicked() {
        binding.shareShow.setOnClickListener {
            buildShareIntent(
                args.tvShow!!.id,
                Constants.SHOW_TYPE
            )
        }
    }

    private fun observeLikedState() {
        viewModel.isShowInFav(args.tvShow?.id!!).observe(viewLifecycleOwner, Observer {
            binding.likeButton.isLiked = it != null
        })
    }

    private fun onLikeButtonClicked() {
        binding.likeButton.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                args.tvShow?.let { viewModel.insertShowIntoFav(it) }
            }

            override fun unLiked(likeButton: LikeButton?) {
                args.tvShow?.let { viewModel.deleteShowFromFav(it.id) }
            }
        })
    }

    /**Display the passed in movie from the [args]*/
    private fun displayShowDetails(tvShow: TvShow?) {
        val posterUrl = Constants.IMAGE_BASE_URL + tvShow?.poster_path

        binding.ivSimilarShowPoster.loadImage(posterUrl, requestManager)

        binding.tvSimilarShowTitle.text = tvShow?.name
        binding.tvSimilarShowGenre.text =
            DetailsHelper.getGenres(tvShow?.genre_ids, requireContext())
        binding.tvSimilarShowYear.text = tvShow?.first_air_date?.substring(0, 4)
        binding.tvSimilarShowOverview.text = tvShow?.overview
        binding.tvSimilarShowRating.text = tvShow?.vote_average?.toPercentage()
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
        binding.videoShowsError.visibility = View.GONE
    }

    private fun showError() {
        binding.videoLoadingSpinner.visibility = View.GONE
        binding.videoShowsError.visibility = View.VISIBLE
    }
}