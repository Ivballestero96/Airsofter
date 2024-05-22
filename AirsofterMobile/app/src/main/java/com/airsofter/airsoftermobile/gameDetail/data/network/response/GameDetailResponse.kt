package com.airsofter.airsoftermobile.gameDetail.data.network.response

import com.airsofter.airsoftermobile.gameDetail.data.network.model.GameDetailDto
import com.google.gson.annotations.SerializedName

data class GameDetailResponse(
    @SerializedName("gameDetailDto") val gameDetailToLoad: GameDetailDto
)
