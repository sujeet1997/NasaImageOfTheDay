package com.sujeet.nasaimageoftheday.network.repository

import com.sujeet.nasaimageoftheday.model.ImageResponse
import com.sujeet.nasaimageoftheday.network.apiCall.ApiInterface


class ApiRepository(private val apiInterface: ApiInterface): BaseRepository() {

    suspend fun getImageDatas(apiKey: String, date: String): ImageResponse? {
        return safeApiCall(
            call = { apiInterface.fetchImageAsync(apiKey, date) },
            error = "error fetching Image data"
        )
    }

}