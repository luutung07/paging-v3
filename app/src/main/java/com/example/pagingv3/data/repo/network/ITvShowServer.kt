package com.example.pagingv3.data.repo.network

import com.example.pagingv3.data.model.TvShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ITvShowServer {

    @GET("api/most-popular")
    fun getTvShowResponse(@Query("page") page: Int): Call<TvShowResponse>

}