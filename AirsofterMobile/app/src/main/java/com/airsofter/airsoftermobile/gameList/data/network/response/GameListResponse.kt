package com.airsofter.airsoftermobile.gameList.data.network.response

import com.airsofter.airsoftermobile.core.model.Game
import com.google.gson.annotations.SerializedName
import java.util.Date

data class GameListResponse(
    @SerializedName("gameListDto")val games: List<Game>
)
