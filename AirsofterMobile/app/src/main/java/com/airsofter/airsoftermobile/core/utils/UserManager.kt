import android.content.Context
import com.airsofter.airsoftermobile.core.model.UserToLoad

object UserManager {
    private const val PREF_USER_ID = "userId"
    private const val PREF_USERNAME = "username"
    private const val PREF_DISPLAY_NAME = "displayName"
    private const val PREF_EMAIL = "email"

    private var currentUser: UserToLoad? = null

    // Método para establecer el usuario actual
    fun setCurrentUser(userToLoad: UserToLoad, context: Context) {
        currentUser = userToLoad
        saveUserToPrefs(userToLoad, context)
    }

    // Método para obtener el usuario actual
    fun getCurrentUser(): UserToLoad? {
        return currentUser
    }

    // Método para verificar si hay un usuario actualmente autenticado
    fun isLoggedIn(): Boolean {
        return currentUser != null
    }

    // Método para cerrar sesión
    fun logout() {
        currentUser = null
    }

    // Método privado para guardar el usuario en las SharedPreferences
    private fun saveUserToPrefs(userToLoad: UserToLoad, context: Context) {
        val prefs = context.getSharedPreferences("UserManager", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(PREF_USER_ID, userToLoad.id)
        editor.putString(PREF_USERNAME, userToLoad.username)
        editor.putString(PREF_DISPLAY_NAME, userToLoad.displayName)
        editor.putString(PREF_EMAIL, userToLoad.email)
        editor.apply()
    }
}
