package io.atreydos.otask.ui.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.atreydos.otask.domain.entity.LoadStatus
import io.atreydos.otask.domain.entity.TrendDetailed
import io.atreydos.otask.domain.usecase.EndlessIterateAcceptableTrendsUseCase

class TrendingViewModel(
    private val endlessIterateAcceptableTrendsUseCase: EndlessIterateAcceptableTrendsUseCase
) : ViewModel() {

    companion object {
        const val LOG_TAG = "AtrL:TrendingViewModel"
    }

    private val mViewStateLD: MutableLiveData<ViewState> = MutableLiveData()
    val viewStateLD: LiveData<ViewState> = mViewStateLD

    init {
        endlessIterateAcceptableTrendsUseCase.trendDetailedLD.observeForever {
            val newViewState = when (it) {
                is LoadStatus.Loading -> ViewState(isNetworking = true)
                is LoadStatus.Error -> ViewState(errorMessage = it.e.localizedMessage)
                is LoadStatus.Success -> ViewState(trendDetailed = it.data)
                else -> throw IllegalStateException("Received unknown data type ${it::class.java.name}")
            }
            mViewStateLD.postValue(newViewState)
        }
    }

    fun navigateToNext() {
        endlessIterateAcceptableTrendsUseCase.iterate()
    }

    fun tryAgain() {
        endlessIterateAcceptableTrendsUseCase.iterate()
    }

}

data class ViewState(
    val isNetworking: Boolean = false,
    val errorMessage: String? = null,
    val trendDetailed: TrendDetailed? = null
)