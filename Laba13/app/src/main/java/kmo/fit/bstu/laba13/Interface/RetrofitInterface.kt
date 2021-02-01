package kmo.fit.bstu.laba13.Interface

import kmo.fit.bstu.laba13.Model.CoinList
import kmo.fit.bstu.laba13.Model.Movie
import retrofit2.Call
import retrofit2.http.*

interface RetrofitInterface {
    @GET("marvel")
    fun getMovieList(): Call<MutableList<Movie>>
    @GET("assets")
    fun getCoinList(): Call<CoinList>
}