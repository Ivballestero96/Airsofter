package com.airsofter.airsoftermobile.gameList.data.network.response

import com.airsofter.airsoftermobile.core.model.Game
import com.google.gson.annotations.SerializedName

data class NextGameResponse (
    @SerializedName("nextGameDto")val nextGameDto: Game
)