package com.airsofter.airsoftermobile.register.data.network

import com.airsofter.airsoftermobile.register.data.network.requests.RegisterRequest
import com.airsofter.airsoftermobile.register.data.network.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IRegisterClient {
    @POST("/v3/f78c9d33-28b1-4f81-9cf1-6d6ff78fa014")
    suspend fun registerUser(@Body request : RegisterRequest): Response<RegisterResponse>
}