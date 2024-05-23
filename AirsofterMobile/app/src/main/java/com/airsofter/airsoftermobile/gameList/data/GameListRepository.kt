package com.airsofter.airsoftermobile.gameList.data

import com.airsofter.airsoftermobile.core.model.GameDetailDto
import com.airsofter.airsoftermobile.gameList.data.network.GameListService
import javax.inject.Inject

class GameListRepository @Inject constructor(private val api: GameListService){
    suspend fun getGameList(): List<GameDetailDto>? {
        return api.getGameList()
    }

    suspend fun getNextGame(id: String?): GameDetailDto? {
        return api.getNextGame(id)
    }
}