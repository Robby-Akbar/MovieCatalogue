package monster.myapp.moviecatalogue.core.utils

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by robby on 06/05/21.
 */
class AppUtils {

    fun hideSystemUIAndNavigation(activity: Activity) {
        val decorView: View = activity.window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window.setDecorFitsSystemWindows(false)
            activity.window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // Hide the nav bar and status bar
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }

    companion object {
        fun formatDate(dateString: String): String {
            val fmt = SimpleDateFormat("yyyy-MM-dd", Locale("en", "US"))
            val newFmt = SimpleDateFormat("EEEE, MMM dd, yyyy", Locale("en", "US"))

            return if (dateString == "" || dateString == "null") dateString
            else {
                val date: Date = fmt.parse(dateString) ?: Date()
                newFmt.format(date)
            }
        }
    }

}