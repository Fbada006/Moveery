package com.disruption.moveery.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.disruption.moveery.R

/**
 * A simple [Fragment] subclass to show the preferences.
 */
class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.pref_settings)

        val pref = findPreference<ListPreference>(getString(R.string.pref_night_mode_key))
        pref?.setOnPreferenceChangeListener { _, newValue ->
            when (newValue) {
                getString(R.string.pref_night_battery_set_value) -> {
                    updateTheme(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                    true
                }
                getString(R.string.pref_night_on_value) -> {
                    updateTheme(AppCompatDelegate.MODE_NIGHT_YES)
                    true
                }
                getString(R.string.pref_night_off_value) -> {
                    updateTheme(AppCompatDelegate.MODE_NIGHT_NO)
                    true
                }
                else -> {
                    updateTheme(AppCompatDelegate.MODE_NIGHT_UNSPECIFIED)
                    false
                }
            }
        }
    }

    private fun updateTheme(nightId: Int) {
        AppCompatDelegate.setDefaultNightMode(nightId)
        requireActivity().recreate()
    }
}
