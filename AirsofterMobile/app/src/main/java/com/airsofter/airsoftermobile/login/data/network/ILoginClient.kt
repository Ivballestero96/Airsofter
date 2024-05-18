package com.airsofter.airsoftermobile.login.data.network

import com.airsofter.airsoftermobile.login.data.network.requests.LoginRequest
import com.airsofter.airsoftermobile.login.data.network.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ILoginClient {
    @POST("user/login/")
    suspend fun doLogin(@Body loginRequest: LoginRequest): Response<LoginResponse>
}
