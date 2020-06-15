package com.droidafricana.moveery.utils

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.droidafricana.moveery.R
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsHelperTest {

    private lateinit var context: Context

    /**Set objects*/
    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    /**Test that the genre strings are properly returned*/
    @Test
    fun getGenres_actionDocumentaryDrama_returnsActionDocumentaryDrama() {
        //Given a list of the following genres
        val genreIds = listOf(28, 99, 18)

        //When getGenres() is called
        val genres = DetailsHelper.getGenres(genreIds, context)

        //Then assert that the string is "Action, Documentary, Drama."
        assertThat(genres).isEqualTo("Action,Documentary,Drama.")
    }

    /**Test that the no genre string is returned given an empty list*/
    @Test
    fun getGenres_actionEmptyList_returnsNoGenreProvided() {
        //Given an empty list of genres
        val genreIds = emptyList<Int>()

        //When getGenres() is called
        val genres = DetailsHelper.getGenres(genreIds, context)

        //Then assert that the string is "Genre Unknown"
        assertThat(genres).isEqualTo("Genre Unknown.")
    }

    /**Test that the no genre string is returned given a null list*/
    @Test
    fun getGenres_actionNullList_returnsNoGenreProvided() {
        //Given an empty list of genres
        val genreIds = null

        //When getGenres() is called
        val genres = DetailsHelper.getGenres(genreIds, context)

        //Then assert that the string is "Genre Unknown"
        assertThat(genres).isEqualTo("Genre Unknown.")
    }

    /**Test that the lessThan100OrEqual color is returned*/
    @Test
    fun getRatingColor_action80Rating_returnsLessThan100OrEqual() {
        //Given a rating of 80
        val rating = 80

        //When getRatingColor() is called
        val ratingColor = DetailsHelper.getRatingColor(rating, context)

        //Then assert that the color is lessThan100OrEqual
        assertThat(ratingColor).isEqualTo(
            ContextCompat.getColor(
                context,
                R.color.lessThan100OrEqual
            )
        )
    }

    /**Test that the accent color is returned*/
    @Test
    fun getRatingColor_action1000Rating_returnsColorAccent() {
        //Given a rating of 1000
        val rating = 1000

        //When getRatingColor() is called
        val ratingColor = DetailsHelper.getRatingColor(rating, context)

        //Then assert that the color is colorAccent
        assertThat(ratingColor).isEqualTo(ContextCompat.getColor(context, R.color.colorAccent))
    }
}