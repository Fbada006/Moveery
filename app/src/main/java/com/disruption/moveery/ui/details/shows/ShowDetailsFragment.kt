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
import com.disruption.moveery.utils.*
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
        }

        val adapter = ShowSimilarPagedAdapter(requireContext())
        binding.similarShowsList.adapter = adapter
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.similarShowsList.layoutManager = layoutManager

        viewModel.showList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        binding.showDetailsViewModel = viewModel
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
}