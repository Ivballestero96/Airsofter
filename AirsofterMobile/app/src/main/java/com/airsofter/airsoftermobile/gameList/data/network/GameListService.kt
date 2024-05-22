package com.airsofter.airsoftermobile.gameList.data.network

import android.util.Log
import com.airsofter.airsoftermobile.gameList.data.network.response.GameListResponse
import com.airsofter.airsoftermobile.gameList.data.network.response.NextGameResponse
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
            Log.e("GameListService", "Error retrieving game list: ${e.message}", e)
            null
        }
    }

    suspend fun getNextGame(id: String?): NextGameResponse? {
        return try {
            val response = gameListClient.getNextGame(id)
                Log.d("TEST", "Entro en service")
                Log.d("TEST", response.toString())
            if (response != null && response.isSuccessful) {
                Log.d("Response", response.body().toString())
                response.body()
            } else {
                Log.d("TEST", "NULL")
                null
            }
        } catch (e: Exception) {
            Log.e("GameListService", "Error retrieving next game: ${e.message}", e)
            null
        }
    }


}