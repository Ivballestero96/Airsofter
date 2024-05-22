package com.airsofter.airsoftermobile.gameDetail.domain

import com.airsofter.airsoftermobile.gameDetail.data.network.GameDetailRepository
import com.airsofter.airsoftermobile.gameDetail.data.network.response.GameDetailResponse
import com.airsofter.airsoftermobile.gameList.data.network.response.GameListResponse
import javax.inject.Inject

class GameDetailUseCase @Inject constructor(private val repository: GameDetailRepository)  {
    suspend operator fun invoke(id: String): GameDetailResponse? {
        return repository.getGameDetail(id)
    }
}