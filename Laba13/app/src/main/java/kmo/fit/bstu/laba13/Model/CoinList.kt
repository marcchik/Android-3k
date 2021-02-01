package kmo.fit.bstu.laba13.Model

class CoinList {
    var data: List<Coin>? = null
    var timestamp: Long? = null
    fun returndata(): List<Coin>? {
        return data;
    }
}