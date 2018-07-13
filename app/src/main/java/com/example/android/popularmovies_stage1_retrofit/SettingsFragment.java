package com.example.android.popularmovies_stage1_retrofit;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

/**
 * Setting the preferences for the Sort order
 */

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
                addPreferencesFromResource(R.xml.preference_sort);
    }
}
