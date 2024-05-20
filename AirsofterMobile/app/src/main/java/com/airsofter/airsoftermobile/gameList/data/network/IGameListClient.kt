package com.airsofter.airsoftermobile.gameList.data.network

import com.airsofter.airsoftermobile.gameList.data.network.response.GameListResponse
import com.airsofter.airsoftermobile.login.data.network.requests.LoginRequest
import com.airsofter.airsoftermobile.login.data.network.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IGameListClient {
    @GET("game/list/")
    suspend fun getGameList(): Response<GameListResponse>
}