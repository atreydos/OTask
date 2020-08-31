package io.atreydos.otask.domain.entity

open class LoadStatus<out T : Any> {
    class Loading() : LoadStatus<Nothing>()
    data class Success<out T : Any>(val data: T) : LoadStatus<T>()
    data class Error(val e: Exception) : LoadStatus<Nothing>()
}
