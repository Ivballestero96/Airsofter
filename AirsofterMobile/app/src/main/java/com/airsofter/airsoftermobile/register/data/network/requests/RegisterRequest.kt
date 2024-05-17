package com.airsofter.airsoftermobile.register.data.network.requests

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    val username: String,
    val displayName: String,
    val email: String,
    val password: String
)