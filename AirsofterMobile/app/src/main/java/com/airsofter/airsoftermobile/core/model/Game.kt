package com.airsofter.airsoftermobile.core.model

data class Game(
    val id: String,
    val fieldName: String,
    val location: String,
    val gameDateTime: String,
    val isAM: Boolean,
    val currentPlayers: Int,
    val maxPlayers: Int
)
