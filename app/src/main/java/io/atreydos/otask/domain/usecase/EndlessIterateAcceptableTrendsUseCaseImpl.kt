package io.atreydos.otask.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import io.atreydos.otask.domain.entity.LoadStatus
import io.atreydos.otask.domain.entity.TrendDetailed
import io.atreydos.otask.domain.entity.TrendShort
import io.atreydos.otask.domain.entity.TrendType
import io.atreydos.otask.domain.repository.TrendsRepository
import kotlinx.coroutines.Dispatchers

class EndlessIterateAcceptableTrendsUseCaseImpl(
    private val trendsRepository: TrendsRepository
) : EndlessIterateAcceptableTrendsUseCase {

    private val mTrendsListTimestamp = MutableLiveData<Long>(0)
    private var mTrendsListLD: LiveData<LoadStatus<ArrayList<TrendShort>>> =
        Transformations.switchMap(mTrendsListTimestamp) {
            liveData(Dispatchers.IO) {
                emit(LoadStatus.Loading())
                emit(trendsRepository.getTrendsList())
            }
        }

    private var mTrendIdIteratorPosition = -1
    private val mTrendDetailedLD: MutableLiveData<LoadStatus<TrendDetailed>> = MutableLiveData()
    override val trendDetailedLD: LiveData<LoadStatus<TrendDetailed>> = mTrendDetailedLD

    private val mCurrentTempTrendId = MutableLiveData<Int>(-1)
    private val mTempTrendDetailedLD: LiveData<LoadStatus<TrendDetailed>> =
        Transformations.switchMap(mCurrentTempTrendId) { id ->
            liveData(Dispatchers.IO) {
                if (id < 0) {
                    emit(LoadStatus.Loading())
                    return@liveData
                }
                emit(LoadStatus.Loading())
                emit(trendsRepository.getTrendDetailed(id))
            }
        }

    init {
        mTrendsListLD.observeForever {
            when (it) {
                is LoadStatus.Loading -> mTrendDetailedLD.value = it
                is LoadStatus.Error -> mTrendDetailedLD.value = it
                is LoadStatus.Success -> {
                    mTrendIdIteratorPosition = 0
                    iterate()
                }
            }
        }
        mTempTrendDetailedLD.observeForever {
            when (it) {
                is LoadStatus.Loading -> mTrendDetailedLD.value = it
                is LoadStatus.Error -> mTrendDetailedLD.value = it
                is LoadStatus.Success -> {
                    mTrendIdIteratorPosition = mCurrentTempTrendId.value!!
                    if (it.data.type == TrendType.GAME)
                        iterate()
                    else
                        mTrendDetailedLD.value = it
                }
            }
        }
    }

    override fun iterate() {
        if (mTrendsListLD.value is LoadStatus.Loading)
            return
        if (mTrendsListLD.value is LoadStatus.Error) {
            reloadTrendsList()
            return
        }
        val data = (mTrendsListLD.value as LoadStatus.Success<ArrayList<TrendShort>>).data
        if (data.isEmpty())
            return
        val item = data.firstOrNull { it.id == mTrendIdIteratorPosition }
        val itemPosition = data.indexOf(item)
        val itemToLoad = if (item == null || itemPosition == data.size - 1)
            data.first()
        else
            data[itemPosition + 1]
        mCurrentTempTrendId.value = itemToLoad.id
    }

    private fun reloadTrendsList() {
        mTrendsListTimestamp.value = System.currentTimeMillis()
    }
}