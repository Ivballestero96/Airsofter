package com.airsofter.airsoftermobile.gameList.data.network

import android.util.Log
import com.airsofter.airsoftermobile.gameList.data.network.response.GameListResponse
import com.airsofter.airsoftermobile.login.data.network.ILoginClient
import com.airsofter.airsoftermobile.login.data.network.requests.LoginRequest
import com.airsofter.airsoftermobile.login.data.network.response.LoginResponse
import javax.inject.Inject

class GameListService @Inject constructor(private val gameListClient: IGameListClient){
    suspend fun getGameList(): GameListResponse? {
        return try {
            val response = gameListClient.getGameList()

            Log.d("Response", response.body().toString())

            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("Login", "Error during login: ${e.message}", e)
            null
        }
    }
}