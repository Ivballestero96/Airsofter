package com.airsofter.airsoftermobile.gameList.data

import com.airsofter.airsoftermobile.gameList.data.network.GameListService
import com.airsofter.airsoftermobile.gameList.data.network.response.GameListResponse
import javax.inject.Inject

class GameListRepository @Inject constructor(private val api: GameListService){
    suspend fun getGameList(): GameListResponse? {
        return api.getGameList()
    }
}