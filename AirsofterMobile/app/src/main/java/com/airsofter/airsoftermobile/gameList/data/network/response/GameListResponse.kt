package com.airsofter.airsoftermobile.gameList.data.network.response

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Game(
    val id: String,
    val fieldName: String,
    val location: String,
    val gameDateTime: String,
    val isAM: Boolean,
    val currentPlayers: Int,
    val maxPlayers: Int
)

data class GameListResponse(
    @SerializedName("gameListDto")val games: List<Game>
)
