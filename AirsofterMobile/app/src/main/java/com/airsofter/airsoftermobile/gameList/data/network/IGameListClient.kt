package com.airsofter.airsoftermobile.gameList.data.network

import com.airsofter.airsoftermobile.core.model.GameDetailDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface IGameListClient {
    @GET("game/list/")
    suspend fun getGameList(): Response<List<GameDetailDto>>

    @GET("game/next/{id}")
    suspend fun getNextGame(@Path("id") id: String?): Response<GameDetailDto>
}