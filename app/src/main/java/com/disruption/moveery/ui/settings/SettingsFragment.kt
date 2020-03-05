package com.disruption.moveery.ui.settings

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.disruption.moveery.R
import com.disruption.moveery.utils.ThemeHelper

/**
 * A simple [PreferenceFragmentCompat] subclass to show the preferences.
 */
class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.pref_settings)

        setSelectedTheme()
    }

    private fun setSelectedTheme() {
        val themePref = findPreference<ListPreference>(getString(R.string.pref_night_mode_key))
        themePref?.setOnPreferenceChangeListener { _, newValue ->
            val themeOption = newValue as String
            ThemeHelper.applyTheme(themeOption)
            true
        }
    }
}
