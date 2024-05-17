package com.airsofter.airsoftermobile.register.domain

import com.airsofter.airsoftermobile.register.data.RegisterRepository
import com.airsofter.airsoftermobile.register.data.network.response.RegisterResponse
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: RegisterRepository) {

    suspend operator fun invoke(username: String, displayName: String, email: String, password: String) : RegisterResponse {
        return repository.registerUser(username, displayName, email, password)
    }
}