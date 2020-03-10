package com.disruption.moveery.ui.details

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.disruption.moveery.MainActivity
import com.disruption.moveery.R
import com.disruption.moveery.databinding.FragmentDetailsBinding
import com.disruption.moveery.di.Injectable
import com.disruption.moveery.utils.Constants
import com.disruption.moveery.utils.DetailsHelper
import javax.inject.Inject

/**
 * A fragment to show the details of the movie
 */
class DetailsFragment : Fragment(), Injectable {

    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()

    @Inject
    lateinit var requestManager: RequestManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater)

        displayMovieDetails()

        showAndHandleBackButton()

        // Inflate the layout for this fragment
        return binding.root
    }

    /**Display the passed in movie from the [args]*/
    private fun displayMovieDetails() {
        val movie = args.movie
        val posterUrl = Constants.IMAGE_BASE_URL + movie?.poster_path

        setMoviePoster(posterUrl)

        val average = ((movie?.vote_average)!! * 10).toInt()

        binding.tvMovieTitle.text = movie.original_title
        binding.tvMovieGenre.text = DetailsHelper.getGenres(movie.genre_ids, requireContext())
        binding.tvMovieYear.text = movie.release_date?.substring(0, 4)
        binding.tvMovieOverview.text = movie.overview
        binding.tvMovieRating.text = average.toString().plus("%")
        binding.ratingCustomView.apply {
            setValue(average)
            setFillColor(DetailsHelper.getRatingColor(average, requireContext()))
            setStrokeColor(ContextCompat.getColor(requireContext(), R.color.colorAccent))
        }
    }

    private fun setMoviePoster(posterUrl: String) {
        requestManager
            .load(posterUrl)
//            .listener(object : RequestListener<Drawable> {
//                override fun onLoadFailed(
//                    e: GlideException?,
//                    model: Any?,
//                    target: Target<Drawable>?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    return false
//                }
//
//                override fun onResourceReady(
//                    resource: Drawable?,
//                    model: Any?,
//                    target: Target<Drawable>?,
//                    dataSource: DataSource?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    val bitmap = (resource as BitmapDrawable).bitmap
//                    setStatusBarColorFromPoster(bitmap)
//                    return false
//                }
//            })
            .into(binding.ivMoviePoster)
    }

    private fun showAndHandleBackButton() {
        val toolbar = binding.toolbar
        toolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    private fun setStatusBarColorFromPoster(posterBitmap: Bitmap) {
        Palette.from(posterBitmap).generate { palette ->
            val darkVibrantColor = palette?.getDarkVibrantColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.colorPrimaryDark
                )
            )

            activity?.window?.statusBarColor =
                ContextCompat.getColor(requireContext(), darkVibrantColor!!)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity?)!!.supportActionBar!!.show()
    }
}
