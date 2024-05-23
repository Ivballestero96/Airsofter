package com.airsofter.airsoftermobile.gameDetail.domain

import com.airsofter.airsoftermobile.gameDetail.data.network.GameDetailRepository
import com.airsofter.airsoftermobile.gameDetail.data.network.response.SignUpResponse
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repository: GameDetailRepository) {
    suspend operator fun invoke(userId: String?, gameId: String?): SignUpResponse {
        return repository.signUp(userId, gameId)
    }
}