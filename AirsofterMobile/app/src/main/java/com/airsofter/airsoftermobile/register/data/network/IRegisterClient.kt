package com.airsofter.airsoftermobile.register.data.network

import com.airsofter.airsoftermobile.login.data.network.requests.LoginRequest
import com.airsofter.airsoftermobile.login.data.network.response.LoginResponse
import com.airsofter.airsoftermobile.register.data.network.requests.RegisterRequest
import com.airsofter.airsoftermobile.register.data.network.response.AvailabilityResponse
import com.airsofter.airsoftermobile.register.data.network.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface IRegisterClient {
    @POST("users/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    @POST("users/checkAvailability")
    suspend fun checkFieldAvailability(
        @Query("field") field: String,
        @Query("value") value: String
    ): Response<AvailabilityResponse>

}