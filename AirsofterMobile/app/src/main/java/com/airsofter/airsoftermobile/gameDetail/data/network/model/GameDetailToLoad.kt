package com.airsofter.airsoftermobile.gameDetail.data.network.model

data class GameDetailToLoad(
    val id: String,
    val fieldName: String,
    val companyName: String?,
    val provinceName: String?,
    val countryName: String?,
    val description: String?,
    val currentPlayers: Int,
    val maxPlayers: Int,
    val gameDateTime: String,
    val isAM: Boolean,
    val players: List<String>?
)
