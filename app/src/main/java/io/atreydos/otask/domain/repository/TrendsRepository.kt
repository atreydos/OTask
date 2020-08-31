package io.atreydos.otask.domain.repository

import io.atreydos.otask.domain.entity.LoadStatus
import io.atreydos.otask.domain.entity.TrendDetailed
import io.atreydos.otask.domain.entity.TrendShort

interface TrendsRepository {

    suspend fun getTrendsList(): LoadStatus<ArrayList<TrendShort>>

    suspend fun getTrendDetailed(id: Int): LoadStatus<TrendDetailed>

}