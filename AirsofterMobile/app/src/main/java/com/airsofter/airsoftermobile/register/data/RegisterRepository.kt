package com.airsofter.airsoftermobile.register.data

import com.airsofter.airsoftermobile.register.data.network.RegisterService
import com.airsofter.airsoftermobile.register.data.network.response.RegisterResponse
import javax.inject.Inject

class RegisterRepository @Inject constructor(private val api: RegisterService){

    suspend fun registerUser(username: String, displayName: String, email: String, password: String): RegisterResponse {
        return api.registerUser(username, displayName, email, password)
    }
}