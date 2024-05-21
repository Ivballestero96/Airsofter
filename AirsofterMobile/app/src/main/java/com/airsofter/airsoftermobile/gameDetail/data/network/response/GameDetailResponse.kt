package com.airsofter.airsoftermobile.gameDetail.data.network.response

import com.airsofter.airsoftermobile.gameDetail.data.network.model.GameDetailToLoad
import com.google.gson.annotations.SerializedName

data class GameDetailResponse(
    @SerializedName("userToLoad") val gameDetailToLoad: GameDetailToLoad
)
