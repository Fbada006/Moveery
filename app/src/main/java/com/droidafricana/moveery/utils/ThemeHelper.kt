package com.droidafricana.moveery.utils

import android.os.Build
import androidx.appcompat.app.AppCompatDelegate

/**A helper object to set the theme*/
object ThemeHelper {
    private const val LIGHT_MODE = "off"
    private const val DARK_MODE = "on"
    const val DEFAULT_MODE = "auto"

    /**Change the theme based on the preference*/
    fun applyTheme(themePref: String?) {
        when (themePref) {
            LIGHT_MODE -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            DARK_MODE -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            DEFAULT_MODE -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                }
            }
        }
    }
}