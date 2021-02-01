package kmo.fit.bstu.laba13.Common

import kmo.fit.bstu.laba13.Interface.RetrofitInterface
import kmo.fit.bstu.laba13.Retrofit.RetrofitClient
import kmo.fit.bstu.laba13.Retrofit.RetrofitCoinClient

object Common {
    private val BASE_URL = "https://www.simplifiedcoding.net/demos/"
    val retrofitService: RetrofitInterface
        get() = RetrofitCoinClient.getCoin(BASE_URL).create(RetrofitInterface::class.java)
}