package com.airsofter.airsoftermobile.login.domain

import com.airsofter.airsoftermobile.login.data.LoginRepository
import com.airsofter.airsoftermobile.login.data.network.response.LoginResponse
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: LoginRepository) {

    suspend operator fun invoke(username: String, password: String): LoginResponse? {
        return repository.doLogin(username, password)
    }
}