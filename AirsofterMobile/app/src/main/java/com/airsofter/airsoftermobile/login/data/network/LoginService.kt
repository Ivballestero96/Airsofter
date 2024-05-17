package com.airsofter.airsoftermobile.login.data.network

import android.util.Log
import com.airsofter.airsoftermobile.login.data.network.requests.LoginRequest
import java.security.MessageDigest
import javax.inject.Inject

class LoginService @Inject constructor(private val loginClient: ILoginClient){

    suspend fun doLogin(username: String, password: String): Boolean {
        return try {
            val loginRequest = LoginRequest(username, password)
            val response = loginClient.doLogin(loginRequest)
            // Realizar la solicitud HTTP usando Retrofit
            // Procesar la respuesta
            Log.i("INFO", response.raw().toString())
            response.isSuccessful
        } catch (e: Exception) {
            // Manejar errores de red o excepciones
            Log.e("Login", "Error during login: ${e.message}", e)
            false
        }
    }

}