package kmo.fit.bstu.laba13.Common

import kmo.fit.bstu.laba13.Interface.RetrofitInterface
import kmo.fit.bstu.laba13.Retrofit.RetrofitClient

object CoinObj {
    private val BASE_URL2 = "https://api.coincap.io/v2/"
    val retrofitService: RetrofitInterface
        get() = RetrofitClient.getClient(BASE_URL2).create(RetrofitInterface::class.java)
}