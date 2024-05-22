package com.airsofter.airsoftermobile.gameList.data.network

import com.airsofter.airsoftermobile.core.model.Game
import com.airsofter.airsoftermobile.gameDetail.data.network.response.GameDetailResponse
import com.airsofter.airsoftermobile.gameList.data.network.response.GameListResponse
import com.airsofter.airsoftermobile.login.data.network.requests.LoginRequest
import com.airsofter.airsoftermobile.login.data.network.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IGameListClient {
    @GET("game/list/")
    suspend fun getGameList(): Response<GameListResponse>

    @GET("next/{id}")
    suspend fun getNextGame(@Path("id") id: String?): Game
}