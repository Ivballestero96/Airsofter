package com.airsofter.airsoftermobile.gameList.data.network

import android.util.Log
import com.airsofter.airsoftermobile.core.model.GameDetailDto
import javax.inject.Inject

class GameListService @Inject constructor(private val gameListClient: IGameListClient){
    suspend fun getGameList(): List<GameDetailDto>? {
        return try {
            val response = gameListClient.getGameList()

            Log.d("Response", response.body().toString())

            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("GameListService", "Error retrieving game list: ${e.message}", e)
            null
        }
    }

    suspend fun getNextGame(id: String?): GameDetailDto? {
        return try {
            val response = gameListClient.getNextGame(id)
            Log.d("TEST", response.toString())
            if (response.isSuccessful) {
                Log.d("Response", response.body().toString())
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("GameListService", "Error retrieving next game: ${e.message}", e)
            null
        }
    }


}