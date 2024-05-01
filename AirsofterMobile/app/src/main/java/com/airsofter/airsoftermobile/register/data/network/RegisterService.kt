package com.airsofter.airsoftermobile.register.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterService @Inject constructor(private val registerClient: IRegisterClient){

    suspend fun registerUser(username: String, displayName: String, email: String, password: String):Boolean{
        return withContext(Dispatchers.IO){
            val response = registerClient.registerUser(username, displayName, email, password)
            response.body()?.success ?: false
        }
    }
}