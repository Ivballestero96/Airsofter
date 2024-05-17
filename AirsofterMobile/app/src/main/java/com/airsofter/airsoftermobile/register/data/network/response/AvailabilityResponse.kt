package com.airsofter.airsoftermobile.register.data.network.response

import com.google.gson.annotations.SerializedName

data class AvailabilityResponse(
    @SerializedName("available") val available: Boolean
)
