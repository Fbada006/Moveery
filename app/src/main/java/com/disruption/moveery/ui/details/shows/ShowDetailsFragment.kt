package com.disruption.moveery.ui.details.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.disruption.moveery.R
import com.disruption.moveery.databinding.ShowDetailsFragmentBinding
import com.disruption.moveery.models.shows.TvShow
import javax.inject.Inject

/**Fragment to show details of a clicked [TvShow]*/
class ShowDetailsFragment : Fragment() {

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

//    /**Display the passed in movie from the [args]*/
//    private fun displayShowDetails(tvShow: TvShow?) {
//        val posterUrl = Constants.IMAGE_BASE_URL + tvShow?.poster_path
//
//        setMoviePoster(posterUrl)
//
//        val average = ((tvShow?.vote_average)!! * 10).toInt()
//
//        binding.tvMovieTitle.text = tvShow.original_title
//        binding.tvMovieGenre.text = DetailsHelper.getGenres(tvShow.genre_ids, requireContext())
//        binding.tvMovieYear.text = tvShow.release_date?.substring(0, 4)
//        binding.tvMovieOverview.text = tvShow.overview
//        binding.tvMovieRating.text = average.toString().plus("%")
//        binding.ratingCustomView.apply {
//            setValue(average)
//            setFillColor(DetailsHelper.getRatingColor(average, requireContext()))
//            setStrokeColor(ContextCompat.getColor(requireContext(), R.color.colorAccent))
//        }
//    }
}