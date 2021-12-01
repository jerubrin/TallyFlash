package com.jerubrin.tallyflash.presentation

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.jerubrin.tallyflash.R
import com.rarepebble.colorpicker.ColorPreference





class MainPreferenceFragment : PreferenceFragmentCompat() {
    
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.fragment_preference)
    }
    
    override fun onDisplayPreferenceDialog(preference: Preference) {
        if (preference is ColorPreference) {
            preference.showDialog(this, 0)
        } else super.onDisplayPreferenceDialog(preference)
    }

}