package com.airsofter.airsoftermobile.gameDetail.data.network

import android.util.Log
import com.airsofter.airsoftermobile.core.model.GameDetailDto
import com.airsofter.airsoftermobile.gameDetail.data.network.response.SignUpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GameDetailService @Inject constructor(private val gameDetailClient: IGameDetailClient){
    suspend fun getGameDetail(id: String): GameDetailDto? {
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

    suspend fun signUp(userId: String?, gameId: String?): SignUpResponse {
        return withContext(Dispatchers.IO) {
            val response = gameDetailClient.signUp(userId, gameId)

            response.body() ?: SignUpResponse(success = false, message = "Unknown error occurred")
        }
    }

    suspend fun cancelSignUp(userId: String?, gameId: String?): Boolean {
        return try {
            val response = gameDetailClient.cancelSignUp(userId, gameId)

            Log.d("ResponseCancel", response.body().toString())

            response.isSuccessful
        } catch (e: Exception) {
            Log.e("GameDetailService", "Error canceling sign up for game: ${e.message}", e)
            false
        }
    }
}