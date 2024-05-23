package com.airsofter.airsoftermobile.core.model

data class GameDetailDto(
    val id: String,
    val fieldName: String,
    val companyName: String?,
    val provinceName: String,
    val countryName: String?,
    val description: String?,
    val currentPlayers: Int,
    val maxPlayers: Int,
    val gameDateTime: String,
    val isAM: Boolean,
    val players: List<String>?,
    val playerIds: List<String>?
)
