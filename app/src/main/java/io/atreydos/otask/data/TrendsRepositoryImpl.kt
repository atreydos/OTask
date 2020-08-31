package io.atreydos.otask.data

import io.atreydos.otask.data.network.TrendingApiService
import io.atreydos.otask.data.network.adapter.TrendDetailedAdapter
import io.atreydos.otask.data.network.adapter.TrendShortAdapter
import io.atreydos.otask.domain.entity.LoadStatus
import io.atreydos.otask.domain.entity.TrendDetailed
import io.atreydos.otask.domain.entity.TrendShort
import io.atreydos.otask.domain.repository.TrendsRepository

class TrendsRepositoryImpl(private val apiService: TrendingApiService) : TrendsRepository {

    override suspend fun getTrendsList(): LoadStatus<ArrayList<TrendShort>> {
        return try {
            val result = with(TrendShortAdapter(), {
                apiService.getTrending().convertToDomainModel()
            })
            LoadStatus.Success(result as ArrayList)
        } catch (e: Exception) {
            LoadStatus.Error(e)
        }
    }

    override suspend fun getTrendDetailed(id: Int): LoadStatus<TrendDetailed> {
        return try {
            val result = with(TrendDetailedAdapter(), {
                apiService.getObjectById(id).convertToDomainModel()
            })
            LoadStatus.Success(result)
        } catch (e: Exception) {
            LoadStatus.Error(e)
        }
    }
}