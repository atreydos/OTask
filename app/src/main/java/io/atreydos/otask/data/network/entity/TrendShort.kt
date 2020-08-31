package io.atreydos.otask.data.network.entity

import com.google.gson.annotations.SerializedName

data class TrendShort(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String? = null
)