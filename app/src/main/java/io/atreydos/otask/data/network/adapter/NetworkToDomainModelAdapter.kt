package io.atreydos.otask.data.network.adapter

abstract class NetworkToDomainModelAdapter<in N, out D> {

    abstract fun toDomainModel(networkEntity: N): D

    fun N.convertToDomainModel(): D = toDomainModel(this)

    fun List<N>.convertToDomainModel(): List<D> = ArrayList<D>(size).apply {
        this@convertToDomainModel.forEach { add(toDomainModel(it)) }
    }

}