package com.airsofter.airsoftermobile.register.data.network.requests

data class RegisterRequest(
    val username: String,
    val displayName: String,
    val email: String,
    val password: String
)
