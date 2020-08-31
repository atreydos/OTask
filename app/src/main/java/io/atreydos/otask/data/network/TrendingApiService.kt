package io.atreydos.otask.data.network

import io.atreydos.otask.data.network.entity.TrendDetailed
import io.atreydos.otask.data.network.entity.TrendShort
import retrofit2.http.GET
import retrofit2.http.Path

interface TrendingApiService {

    @GET("v1/trending")
    suspend fun getTrending(): ArrayList<TrendShort>

    @GET("v1/object/{id}")
    suspend fun getObjectById(@Path("id") id: Int): TrendDetailed

}