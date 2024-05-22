package com.airsofter.airsoftermobile.gameDetail.data.network

import com.airsofter.airsoftermobile.gameDetail.data.network.response.GameDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface IGameDetailClient {
    @GET("game/{id}")
    suspend fun getGameDetail(@Path("id") id: String?): Response<GameDetailResponse>
}