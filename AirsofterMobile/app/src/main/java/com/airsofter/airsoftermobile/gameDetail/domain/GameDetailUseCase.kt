package com.airsofter.airsoftermobile.gameDetail.domain

import com.airsofter.airsoftermobile.gameDetail.data.network.GameDetailRepository
import com.airsofter.airsoftermobile.core.model.GameDetailDto
import javax.inject.Inject

class GameDetailUseCase @Inject constructor(private val repository: GameDetailRepository)  {
    suspend operator fun invoke(id: String): GameDetailDto? {
        return repository.getGameDetail(id)
    }
}