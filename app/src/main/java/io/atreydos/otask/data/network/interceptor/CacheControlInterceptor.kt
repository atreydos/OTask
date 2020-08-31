package io.atreydos.otask.data.network.interceptor

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit


/*
 * В реальном проекте я бы постарался решить проблему "лишних запросов" сохраняя данные
 * в локальную БД и запрашивая новые данные с сервера передавая в параметах запроса соответствующие
 * фильтры (Например, "дай новые обьекты после таймштампа" или "дай изменившиеся обьекты"...).
 * Но в конктретном, тестовом АПИ такую логику корректно реальзовать нельзя.
 * Потому, даже данную логику с Cache можно считать костылём;
 */
class CacheControlInterceptor : Interceptor {

    private companion object {
        const val CACHE_CONTROL = "Cache-Control"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val cacheControl = CacheControl.Builder()
            .maxAge(1, TimeUnit.MINUTES)
            .maxStale(1, TimeUnit.DAYS)
            .build()
        return chain.proceed(chain.request()).newBuilder()
            .header(CACHE_CONTROL, cacheControl.toString())
            .build()
    }

}