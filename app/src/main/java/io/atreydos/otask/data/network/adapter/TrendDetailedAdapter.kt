package io.atreydos.otask.data.network.adapter

import io.atreydos.otask.domain.entity.TrendDetailed

class TrendDetailedAdapter :
    NetworkToDomainModelAdapter<io.atreydos.otask.data.network.entity.TrendDetailed, TrendDetailed>() {

    override fun toDomainModel(networkEntity: io.atreydos.otask.data.network.entity.TrendDetailed): TrendDetailed {
        return with(networkEntity) {
            when (type) {
                "text" -> TrendDetailed.Text(content = contents)
                "webview" -> TrendDetailed.WebView(url = url)
                "game" -> TrendDetailed.Game()
                else -> TrendDetailed.Unknown()
            }
        }
    }

}