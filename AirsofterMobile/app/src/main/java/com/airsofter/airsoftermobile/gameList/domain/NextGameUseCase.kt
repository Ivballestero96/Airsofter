package com.airsofter.airsoftermobile.gameList.domain

import com.airsofter.airsoftermobile.core.model.Game
import com.airsofter.airsoftermobile.gameList.data.GameListRepository
import com.airsofter.airsoftermobile.gameList.data.network.response.NextGameResponse
import javax.inject.Inject

class NextGameUseCase @Inject constructor(private val repository: GameListRepository) {
    suspend operator fun invoke(id: String?): NextGameResponse? {
        return repository.getNextGame(id)
    }
}