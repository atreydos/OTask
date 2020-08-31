package io.atreydos.otask.data.network

import android.content.Context
import com.google.gson.GsonBuilder
import io.atreydos.otask.BuildConfig
import io.atreydos.otask.data.network.interceptor.CacheControlInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient,
    converterFactory: Converter.Factory,
    baseUrl: String
): T {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(converterFactory)
        .build()
        .create(T::class.java)
}

fun createOkHttpClient(okHttpCache: Cache): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) level = HttpLoggingInterceptor.Level.BODY
    }

    return OkHttpClient.Builder()
        .cache(okHttpCache)
        .addNetworkInterceptor(CacheControlInterceptor())
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

fun getOkHttpCache(context: Context): Cache {
    val maxCacheSize = (1024 * 1024 * 10).toLong()
    return Cache(context.cacheDir, maxCacheSize)
}

fun createGsonConverterFactory(): GsonConverterFactory {
    return GsonConverterFactory.create(
        GsonBuilder().create()
    )
}
