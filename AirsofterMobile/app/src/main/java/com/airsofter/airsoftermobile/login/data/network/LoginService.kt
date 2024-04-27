package com.airsofter.airsoftermobile.login.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginService @Inject constructor(private val loginClient: ILoginClient){

    suspend fun doLogin(username: String, password: String):Boolean{
        return withContext(Dispatchers.IO){
            val response = loginClient.doLogin(username, password)
            response.body()?.success ?: false
        }
    }
}