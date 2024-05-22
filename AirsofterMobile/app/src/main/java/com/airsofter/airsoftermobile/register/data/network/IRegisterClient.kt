package com.airsofter.airsoftermobile.register.data.network

import com.airsofter.airsoftermobile.register.data.network.requests.RegisterRequest
import com.airsofter.airsoftermobile.register.data.network.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface IRegisterClient {
    @POST("user/register")
    @Headers(
        value = [
            "Accept: application/json",
            "Content-type:application/json"]
    )
    suspend fun registerUser(@Body request : RegisterRequest): Response<RegisterResponse>
}