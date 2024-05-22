package com.airsofter.airsoftermobile.gameDetail.data.network.requests

import com.google.gson.annotations.SerializedName

data class GameDetailRequest(
    @SerializedName("id") val id: String,
)