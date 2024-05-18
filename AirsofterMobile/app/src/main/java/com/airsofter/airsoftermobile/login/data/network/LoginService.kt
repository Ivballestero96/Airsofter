package com.airsofter.airsoftermobile.login.data.network

import android.util.Log
import com.airsofter.airsoftermobile.login.data.network.requests.LoginRequest
import com.airsofter.airsoftermobile.login.data.network.response.LoginResponse
import javax.inject.Inject

class LoginService @Inject constructor(private val loginClient: ILoginClient) {

    suspend fun doLogin(username: String, password: String): LoginResponse? {
        return try {
            val loginRequest = LoginRequest(username, password)
            val response = loginClient.doLogin(loginRequest)

            Log.d("Response", response.body().toString())

            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            // Manejar errores de red o excepciones
            Log.e("Login", "Error during login: ${e.message}", e)
            null
        }
    }
}
