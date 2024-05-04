package com.airsofter.airsoftermobile.login.data.network

import android.util.Log
import com.airsofter.airsoftermobile.login.data.network.requests.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import java.security.MessageDigest
import javax.inject.Inject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class LoginService @Inject constructor(private val loginClient: ILoginClient){

    suspend fun doLogin(username: String, password: String): Boolean {
        try {
            val hashedPassword = hashPassword(password) // Cifrar el password
            val loginRequest = LoginRequest(username, hashedPassword)
            val response = loginClient.doLogin(loginRequest)
            // Realizar la solicitud HTTP usando Retrofit
            // Procesar la respuesta
            Log.i("INFO", response.raw().toString())
            return response.isSuccessful
        } catch (e: Exception) {
            // Manejar errores de red o excepciones
            Log.e("Login", "Error during login: ${e.message}", e)
            return false
        }
    }

    fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val bytes = password.toByteArray(Charsets.UTF_8)
        val hashedBytes = digest.digest(bytes)
        return hashedBytes.joinToString("") { "%02x".format(it) }
    }
}