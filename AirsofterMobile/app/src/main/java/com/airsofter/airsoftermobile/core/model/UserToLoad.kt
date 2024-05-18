package com.airsofter.airsoftermobile.core.model

import com.google.gson.annotations.SerializedName

import java.util.*

data class UserToLoad(
    @SerializedName("id") val id: String,
    @SerializedName("username") val username: String,
    @SerializedName("displayName") val displayName: String,
    @SerializedName("email") val email: String
)
