package com.airsofter.airsoftermobile.gameDetail.data.network

import com.airsofter.airsoftermobile.core.model.GameDetailDto
import com.airsofter.airsoftermobile.gameDetail.data.network.response.SignUpResponse
import javax.inject.Inject

class GameDetailRepository @Inject constructor(private val api: GameDetailService) {
    suspend fun getGameDetail(id: String): GameDetailDto? {
        return api.getGameDetail(id)
    }

    suspend fun signUp(userId: String?, gameId: String?): SignUpResponse {
        return api.signUp(userId, gameId)
    }

    suspend fun cancelSignUp(userId: String?, gameId: String?): Boolean {
        return api.cancelSignUp(userId, gameId)
    }
}