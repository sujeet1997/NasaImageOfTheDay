package com.sujeet.nasaimageoftheday.utils


import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.sujeet.nasaimageoftheday.model.ImageResponse

object PreferenceHelper {

    private const val PreferenceName = "NasaPreferenceHelper_NP2023"


    private const val ImageDetails = "NasaPreferenceHelper_NP2023_imageDetails"


    fun clear(context: Context){
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }


    private fun jsonAdapter(): JsonAdapter<ImageResponse> {
        val moshi = Moshi.Builder().build()
        return moshi.adapter(ImageResponse::class.java)
    }


    fun setImageDetails(context: Context, loginResponse: ImageResponse){
        val jsonAdapter: JsonAdapter<ImageResponse> = jsonAdapter()
        val json = jsonAdapter.toJson(loginResponse)

        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(ImageDetails, json).apply()
    }

    fun getImageDetails(context: Context) : ImageResponse? {
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        val stringValue = sharedPreferences.getString(ImageDetails, "")

        val jsonAdapter: JsonAdapter<ImageResponse> = jsonAdapter()
        return if(!stringValue.isNullOrEmpty()) {
            jsonAdapter.fromJson(stringValue)
        }else{
            null
        }
    }


}