package com.droidafricana.moveery.ui.settings

import android.os.Bundle
import androidx.preference.*
import com.droidafricana.moveery.R
import com.droidafricana.moveery.utils.ThemeHelper

/**
 * A simple [PreferenceFragmentCompat] subclass to show the preferences.
 */
class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.pref_settings)

        setSelectedTheme()

        val sharedPreferences = preferenceScreen.sharedPreferences
        val prefScreen = preferenceScreen
        val count = prefScreen.preferenceCount
        for (i in 0 until count) {
            val p = prefScreen.getPreference(i)
            if (p !is CheckBoxPreference && p !is SwitchPreference) {
                val value = sharedPreferences.getString(p.key, "")!!
                setPreferenceSummary(p, value)
            }
        }
    }

    private fun setSelectedTheme() {
        val themePref = findPreference<ListPreference>(getString(R.string.pref_night_mode_key))
        themePref?.setOnPreferenceChangeListener { _, newValue ->
            val themeOption = newValue as String
            ThemeHelper.applyTheme(themeOption)
            true
        }
    }

    private fun setPreferenceSummary(preference: Preference, value: Any) {
        val stringValue = value.toString()

        if (preference is ListPreference) {
            /* For list preferences, look up the correct display value in */
            /* the preference's 'entries' list (since they have separate labels/values). */
            val prefIndex = preference.findIndexOfValue(stringValue)
            if (prefIndex >= 0) {
                preference.setSummary(preference.entries[prefIndex])
            }
        }
    }
}
