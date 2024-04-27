package com.airsofter.airsoftermobile.login.domain

import com.airsofter.airsoftermobile.login.data.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: LoginRepository) {

    suspend operator fun invoke(username: String, password: String) : Boolean{
        return repository.doLogin(username, password)
    }
}