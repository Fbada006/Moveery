package com.disruption.moveery.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.disruption.moveery.R

/**The activity to host the [SettingsFragment]*/
class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }
}
