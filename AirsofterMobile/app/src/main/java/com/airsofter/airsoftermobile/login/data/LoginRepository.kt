package com.airsofter.airsoftermobile.login.data

import com.airsofter.airsoftermobile.login.data.network.LoginService
import com.airsofter.airsoftermobile.login.data.network.response.LoginResponse
import javax.inject.Inject

class LoginRepository @Inject constructor(private val api: LoginService) {

    suspend fun doLogin(username: String, password: String): LoginResponse? {
        return api.doLogin(username, password)
    }
}