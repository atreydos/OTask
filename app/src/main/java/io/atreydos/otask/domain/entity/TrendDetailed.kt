package io.atreydos.otask.domain.entity

/*
 * Простыми словами, sealed class это аналог abstract class, только с приватным пронструктором.
 *
 * Компилятор гарантирует, что только классы, определенные в том же исходном файле, что и
 * sealed class, могут наследоваться от него.
 *
 * То есть, здесь и только здесь мы получаем возвожность описать все возможные варианты структуры
 * данных, что приходит в этот обьект.
 */
sealed class TrendDetailed {

    abstract val type: TrendType

    data class Text(var content: String?) : TrendDetailed() {
        override val type = TrendType.TEXT
    }

    data class WebView(var url: String?) : TrendDetailed() {
        override val type = TrendType.WEB_VIEW
    }

    class Game : TrendDetailed() {
        override val type = TrendType.GAME
    }

    class Unknown : TrendDetailed() {
        override val type = TrendType.UNKNOWN
    }

}
