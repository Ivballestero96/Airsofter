package com.airsofter.airsoftermobile.login.data.network.response

import com.airsofter.airsoftermobile.core.model.UserToLoad
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("userToLoad") val userToLoad: UserToLoad
)
