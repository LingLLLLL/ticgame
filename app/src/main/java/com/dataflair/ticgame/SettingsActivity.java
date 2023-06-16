package com.dataflair.ticgame;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            // Set up player X name preference
            EditTextPreference playerXNamePref = findPreference("playerXName");
            playerXNamePref.setSummary(playerXNamePref.getText());
            playerXNamePref.setOnPreferenceChangeListener(new androidx.preference.Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(androidx.preference.Preference preference, Object newValue) {
                    preference.setSummary((String) newValue);
                    return true;
                }
            });

            // Set up player Y name preference
            EditTextPreference playerYNamePref = findPreference("playerYName");
            playerYNamePref.setSummary(playerYNamePref.getText());
            playerYNamePref.setOnPreferenceChangeListener(new androidx.preference.Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(androidx.preference.Preference preference, Object newValue) {
                    preference.setSummary((String) newValue);
                    return true;
                }
            });
        }
    }
}
