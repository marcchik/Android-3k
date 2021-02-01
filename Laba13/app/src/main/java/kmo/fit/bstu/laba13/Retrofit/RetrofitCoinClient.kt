package kmo.fit.bstu.laba13.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitCoinClient {
    private var retrofit2: Retrofit? = null

    fun getCoin(baseUrl: String): Retrofit {
        if (retrofit2 == null) {
            retrofit2 = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit2!!
    }
}