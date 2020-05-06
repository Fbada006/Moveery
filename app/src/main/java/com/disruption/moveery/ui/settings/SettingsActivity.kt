package com.disruption.moveery.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.disruption.moveery.R
import kotlinx.android.synthetic.main.activity_settings.*

/**The activity to host the [SettingsFragment]*/
class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
