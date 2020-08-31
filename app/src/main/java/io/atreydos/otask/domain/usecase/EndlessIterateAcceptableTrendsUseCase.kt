package io.atreydos.otask.domain.usecase

import androidx.lifecycle.LiveData
import io.atreydos.otask.domain.entity.LoadStatus
import io.atreydos.otask.domain.entity.TrendDetailed

interface EndlessIterateAcceptableTrendsUseCase {

    /**
     * LiveData обьект который будет выдавать данные и состояние задачи.
     */
    val trendDetailedLD: LiveData<LoadStatus<TrendDetailed>>

    /**
     * Тригерит загрузку следующего валидного (все что не типа "game") объекта в
     * переменную trendDetailedLD.
     * Если объекты закончились - пойдёт по кругу.
     */
    fun iterate()

}