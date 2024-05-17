package com.airsofter.airsoftermobile.register.data.network

import com.airsofter.airsoftermobile.register.data.network.requests.RegisterRequest
import com.airsofter.airsoftermobile.register.data.network.response.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterService @Inject constructor(private val registerClient: IRegisterClient) {

    suspend fun registerUser(username: String, displayName: String, email: String, password: String): RegisterResponse {
        return withContext(Dispatchers.IO) {
            val userToRegister : RegisterRequest = RegisterRequest(username, displayName, email, password)
            val response = registerClient.registerUser(userToRegister)
            response.body() ?: RegisterResponse(success = false, message = "Unknown error occurred")
        }
    }
}
