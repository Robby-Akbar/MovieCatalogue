package monster.myapp.moviecatalogue.setting

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import monster.myapp.moviecatalogue.R
import java.util.*

/**
 * Created by robby on 22/06/21.
 */
class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener{

    private lateinit var reminder: String
    private lateinit var language: String

    private lateinit var reminderPreference: SwitchPreference
    private lateinit var languagePreference: Preference

    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.root_preferences)
        init()
        setState()
        languagePreference.setOnPreferenceClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            true
        }
    }

    private fun setState() {
        val sh = preferenceManager.sharedPreferences
        reminderPreference.isChecked = sh.getBoolean(reminder, false)
    }

    private fun init() {
        alarmReceiver = AlarmReceiver()
        reminder = resources.getString(R.string.reminder_key)
        language = resources.getString(R.string.language_key)

        reminderPreference = findPreference<SwitchPreference>(reminder) as SwitchPreference
        languagePreference = findPreference<Preference>(language) as Preference
        val currentLanguage = Locale.getDefault().displayLanguage
        languagePreference.summary = currentLanguage
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == reminder) {
            reminderPreference.isChecked = sharedPreferences.getBoolean(reminder, false)
            if (reminderPreference.isChecked) {
                alarmReceiver.setAlarm(requireContext())
            } else {
                alarmReceiver.cancelAlarm(requireContext())
            }
        }
    }
}