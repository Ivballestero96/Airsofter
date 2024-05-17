package com.airsofter.airsoftermobile.register.data.network.requests

import com.google.gson.annotations.SerializedName

data class RegisterRequest (
    @SerializedName("username") val username: String,
    @SerializedName("displayName") val displayName: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
)