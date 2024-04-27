package com.airsofter.airsoftermobile.login.data

import com.airsofter.airsoftermobile.login.data.network.LoginService
import javax.inject.Inject

class LoginRepository @Inject constructor(private val api:LoginService){

    suspend fun doLogin(username: String, password: String): Boolean{
        return api.doLogin(username, password)
    }
}