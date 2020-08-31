package io.atreydos.otask

import io.atreydos.otask.data.TrendsRepositoryImpl
import io.atreydos.otask.data.network.*
import io.atreydos.otask.domain.repository.TrendsRepository
import io.atreydos.otask.domain.usecase.EndlessIterateAcceptableTrendsUseCase
import io.atreydos.otask.domain.usecase.EndlessIterateAcceptableTrendsUseCaseImpl
import io.atreydos.otask.ui.trending.TrendingViewModel
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Converter

val appModule = module {
    factory<TrendsRepository> { TrendsRepositoryImpl(get()) }
    factory<EndlessIterateAcceptableTrendsUseCase> { EndlessIterateAcceptableTrendsUseCaseImpl(get()) }
    viewModel { TrendingViewModel(get()) }
}

val restApiModule = module {
    single<Cache> { getOkHttpCache(androidContext()) }
    single<OkHttpClient> { createOkHttpClient(get()) }
    single<Converter.Factory> { createGsonConverterFactory() }
    factory<TrendingApiService> { createWebService(get(), get(), BuildConfig.API_BASE_URL) }
}

val allModulesTogether = listOf(appModule, restApiModule)