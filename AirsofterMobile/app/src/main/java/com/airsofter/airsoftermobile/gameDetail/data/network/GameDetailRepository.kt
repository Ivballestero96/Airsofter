package com.airsofter.airsoftermobile.gameDetail.data.network

import com.airsofter.airsoftermobile.gameDetail.data.network.response.GameDetailResponse
import com.airsofter.airsoftermobile.gameList.data.network.response.GameListResponse
import javax.inject.Inject

class GameDetailRepository @Inject constructor(private val api: GameDetailService) {
    suspend fun getGameDetail(id: String): GameDetailResponse? {
        return api.getGameDetail(id)
    }
}