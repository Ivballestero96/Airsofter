import android.content.Context
import android.content.SharedPreferences
import com.airsofter.airsoftermobile.core.model.UserToLoad

object UserManager {
    private const val PREF_NAME = "UserManagerPrefs"
    private const val PREF_USER_ID = "userId"
    private const val PREF_USERNAME = "username"
    private const val PREF_DISPLAY_NAME = "displayName"
    private const val PREF_EMAIL = "email"

    private var sharedPreferences: SharedPreferences? = null

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setCurrentUser(userToLoad: UserToLoad) {
        val editor = sharedPreferences?.edit()
        editor?.putString(PREF_USER_ID, userToLoad.id)
        editor?.putString(PREF_USERNAME, userToLoad.username)
        editor?.putString(PREF_DISPLAY_NAME, userToLoad.displayName)
        editor?.putString(PREF_EMAIL, userToLoad.email)
        editor?.apply()
    }

    fun getCurrentUser(): UserToLoad? {
        val userId = sharedPreferences?.getString(PREF_USER_ID, null)
        val username = sharedPreferences?.getString(PREF_USERNAME, null)
        val displayName = sharedPreferences?.getString(PREF_DISPLAY_NAME, null)
        val email = sharedPreferences?.getString(PREF_EMAIL, null)

        return if (userId != null && username != null && displayName != null && email != null) {
            UserToLoad(userId, username, displayName, email)
        } else {
            null
        }
    }

    fun isLoggedIn(): Boolean {
        return getCurrentUser() != null
    }

    fun logout() {
        val editor = sharedPreferences?.edit()
        editor?.clear()
        editor?.apply()
    }
}
