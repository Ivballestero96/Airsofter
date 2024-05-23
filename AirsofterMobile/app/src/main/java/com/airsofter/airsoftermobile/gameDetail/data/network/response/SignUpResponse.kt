package com.airsofter.airsoftermobile.gameDetail.data.network.response

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String
)
