package android.example.com.visualizerpreferences;

/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;


public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private SharedPreferences sharedPreferences;
    private String DEFAULT_COLOR;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        // Add visualizer preferences, defined in the XML file in res->xml->pref_visualizer
        addPreferencesFromResource(R.xml.pref_visualizer);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        // initializing consts
        DEFAULT_COLOR = getString(R.string.pref_colors_default_value);
        refreshColorPreferenceList(sharedPreferences, getString(R.string.pref_colors_key));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        refreshColorPreferenceList(sharedPreferences, key);
    }

    private void refreshColorPreferenceList(SharedPreferences sharedPreferences, String key) {
        if (key != null && key.equals(getString(R.string.pref_colors_key))){
            final String colorValue = sharedPreferences.getString(key, DEFAULT_COLOR);
            final Preference preferenceList = getPreferenceScreen().findPreference(key);
            preferenceList.setSummary(colorValue);
        }
    }

    @Override
    public void onDestroy() {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        sharedPreferences = null;
        super.onDestroy();
    }
}