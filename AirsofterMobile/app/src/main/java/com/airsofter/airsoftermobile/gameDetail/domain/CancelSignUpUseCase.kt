package com.airsofter.airsoftermobile.gameDetail.domain

import com.airsofter.airsoftermobile.gameDetail.data.network.GameDetailRepository
import javax.inject.Inject

class CancelSignUpUseCase @Inject constructor(private val repository: GameDetailRepository) {
    suspend operator fun invoke(userId: String?, gameId: String?): Boolean {
        return repository.cancelSignUp(userId, gameId)
    }
}