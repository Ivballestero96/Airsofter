package com.airsofter.airsoftermobile.login.data.network.response

import com.airsofter.airsoftermobile.login.data.network.model.UserDTO
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("user") val user: UserDTO?
)
