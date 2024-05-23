package com.airsofter.airsoftermobile.gameDetail.data.network

import com.airsofter.airsoftermobile.core.model.GameDetailDto
import com.airsofter.airsoftermobile.gameDetail.data.network.response.SignUpResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IGameDetailClient {
    @GET("game/{id}")
    suspend fun getGameDetail(@Path("id") id: String?): Response<GameDetailDto>

    @POST("game/{userId}/signUp/{gameId}")
    suspend fun signUp(
        @Path("userId") userId: String?,
        @Path("gameId") gameId: String?): Response<SignUpResponse>

    @DELETE("game/{userId}/cancel/{gameId}")
    suspend fun cancelSignUp(
        @Path("userId") userId: String?,
        @Path("gameId") gameId: String?): Response<Boolean>
}