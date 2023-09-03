package com.sujeet.nasaimageoftheday.network.apiCall

import com.sujeet.nasaimageoftheday.model.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    @GET("planetary/apod")
    suspend fun fetchImageAsync(
        @Query("api_key") apiKey: String,
        @Query("date") date: String
    ): Response<ImageResponse>



}