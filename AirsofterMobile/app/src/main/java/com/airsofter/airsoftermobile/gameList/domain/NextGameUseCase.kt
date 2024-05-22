package com.airsofter.airsoftermobile.gameList.domain

import com.airsofter.airsoftermobile.core.model.Game
import com.airsofter.airsoftermobile.gameList.data.GameListRepository
import javax.inject.Inject

class NextGameUseCase @Inject constructor(private val repository: GameListRepository) {
    suspend operator fun invoke(id: String?): Game? {
        return repository.getNextGame(id)
    }
}