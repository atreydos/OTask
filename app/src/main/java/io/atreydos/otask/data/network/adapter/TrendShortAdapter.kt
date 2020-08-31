package io.atreydos.otask.data.network.adapter

import io.atreydos.otask.domain.entity.TrendShort

class TrendShortAdapter :
    NetworkToDomainModelAdapter<io.atreydos.otask.data.network.entity.TrendShort, TrendShort>() {

    override fun toDomainModel(networkEntity: io.atreydos.otask.data.network.entity.TrendShort): TrendShort {
        return with(networkEntity) { TrendShort(id = id, title = title) }
    }

}