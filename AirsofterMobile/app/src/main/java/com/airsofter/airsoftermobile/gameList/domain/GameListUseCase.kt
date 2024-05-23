package com.airsofter.airsoftermobile.gameList.domain

import com.airsofter.airsoftermobile.core.model.GameDetailDto
import com.airsofter.airsoftermobile.gameList.data.GameListRepository
import javax.inject.Inject

class GameListUseCase @Inject constructor(private val repository: GameListRepository)  {
    suspend operator fun invoke(): List<GameDetailDto>? {
        return repository.getGameList()
    }
}