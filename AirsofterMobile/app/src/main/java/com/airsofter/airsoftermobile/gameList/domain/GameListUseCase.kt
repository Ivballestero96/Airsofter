package com.airsofter.airsoftermobile.gameList.domain

import com.airsofter.airsoftermobile.gameList.data.GameListRepository
import com.airsofter.airsoftermobile.gameList.data.network.response.GameListResponse
import javax.inject.Inject

class GameListUseCase @Inject constructor(private val repository: GameListRepository)  {
    suspend operator fun invoke(): GameListResponse? {
        return repository.getGameList()
    }
}