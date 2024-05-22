package com.airsofter.airsoftermobile.gameDetail.data.network

import android.util.Log
import com.airsofter.airsoftermobile.gameDetail.data.network.response.GameDetailResponse
import javax.inject.Inject

class GameDetailService @Inject constructor(private val gameDetailClient: IGameDetailClient){
    suspend fun getGameDetail(id: String): GameDetailResponse? {
        return try {
            val response = gameDetailClient.getGameDetail(id)

            Log.d("Response", response.body().toString())

            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("GameDetailService", "Error retrieving game details: ${e.message}", e)
            null
        }
    }
}