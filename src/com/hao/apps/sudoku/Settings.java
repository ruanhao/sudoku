package com.hao.apps.sudoku;

import com.hao.apps.sudoku.R;

import android.content.Context;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceChangeListener;


public class Settings extends PreferenceActivity {
	private ListPreference lp = null;
	private static final String MUSIC_OPTION = "music";
	private static final boolean MUSIC_OPTION_DEFAULT = true;
	private static final String KEY = "difficultySetting";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		lp = (ListPreference) findPreference(KEY);
		lp.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
				// TODO Auto-generated method stub
				String diffString = newValue.toString();
				Integer which = Integer.parseInt(diffString);
				String value = null;
				switch (which) {
				case Game.DIFFICULTY_EASY:
					value = "easy";
					break;
				case Game.DIFFICULTY_MEDIUM:
					value = "not easy";
					break;
				case Game.DIFFICULTY_HARD:
					value = "absolutely not easy";
					break;
				default:
					break;
				}
				preference.setTitle(value);
				return true;
			}
		});

	}

	public static boolean getMusicState(Context ctx) {

		return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(
				MUSIC_OPTION, MUSIC_OPTION_DEFAULT);
	}

	public static String getDiffState(Context ctx) {

		return PreferenceManager.getDefaultSharedPreferences(ctx).getString(
				KEY, "0");
	}

}
