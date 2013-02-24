package info.danshin.android.polarh7;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class AccountSettingsActivity extends PreferenceActivity {
	
	AccountSettingsFragment fragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		fragment = new AccountSettingsFragment();
		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, fragment).commit();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		// Register the listener whenever a key changes
		PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(fragment);
	}
	
	@Override
    protected void onPause() {
        super.onPause();

        // Unregister the listener whenever a key changes            
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(fragment);    
    }

	
}

class AccountSettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {
	public static final String KEY_FIRST_NAME = "first_name";
	public static final String KEY_LAST_NAME = "last_name";
	public static final String KEY_NICKNAME = "nickname";
	public static final String KEY_AGE = "age";
	public static final String KEY_GENDER = "gender";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_PASSWORD = "password";
	
	private EditTextPreference mEmailPref;
	private EditTextPreference mPasswordPref;
	private EditTextPreference mFirstNamePref;
	private EditTextPreference mLastNamePref;
	private EditTextPreference mNicknamePref;
	private ListPreference mGenderPref;
	private EditTextPreference mAgePref;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Add a button to the header list.
		addPreferencesFromResource(R.xml.account_settings);
		
		mEmailPref = (EditTextPreference) findPreference(KEY_EMAIL);
		mPasswordPref = (EditTextPreference) findPreference(KEY_PASSWORD);
		mFirstNamePref = (EditTextPreference) findPreference(KEY_FIRST_NAME);
		mLastNamePref = (EditTextPreference) findPreference(KEY_LAST_NAME);
		mNicknamePref = (EditTextPreference) findPreference(KEY_NICKNAME);
		mAgePref = (EditTextPreference) findPreference(KEY_AGE);
		mGenderPref = (ListPreference) findPreference(KEY_GENDER);
		
		updatePrefSummary(mFirstNamePref);
		updatePrefSummary(mLastNamePref);
		updatePrefSummary(mNicknamePref);
		updatePrefSummary(mAgePref);
		updatePrefSummary(mGenderPref);
		updatePrefSummary(mEmailPref);
	}
	
	private void updatePrefSummary(Preference pref) {
		if (pref instanceof EditTextPreference) {
			EditTextPreference etp = (EditTextPreference) pref;
			if (etp.getText() != null && !etp.getText().isEmpty())
				etp.setSummary(etp.getText());
		} else if (pref instanceof ListPreference) {
			ListPreference lp = (ListPreference) pref;
			if (lp.getValue() != null) {
				lp.setSummary(lp.getValue());
			}
		}
	}
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
		if (key.equals(KEY_FIRST_NAME)) {
			updatePrefSummary(mFirstNamePref);
		} else if (key.equals(KEY_LAST_NAME)) {
			updatePrefSummary(mLastNamePref);
		} else if (key.equals(KEY_NICKNAME)) {
			updatePrefSummary(mNicknamePref);
		} else if (key.equals(KEY_AGE)) {
			updatePrefSummary(mAgePref);
		} else if (key.equals(KEY_GENDER)) {
			updatePrefSummary(mGenderPref);
		} else if (key.equals(KEY_EMAIL)) {
			updatePrefSummary(mEmailPref);
		}	
	}
}
