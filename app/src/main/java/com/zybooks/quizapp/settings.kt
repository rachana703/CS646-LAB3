import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.zybooks.quizapp.R

//import com.example.yourapp.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }
}
