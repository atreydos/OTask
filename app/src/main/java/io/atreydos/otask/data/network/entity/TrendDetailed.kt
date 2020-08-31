package io.atreydos.otask.data.network.entity

import com.google.gson.annotations.SerializedName

data class TrendDetailed(
    @SerializedName("type") val type: String? = null,
    @SerializedName("contents") val contents: String? = null,
    @SerializedName("url") val url: String? = null
)