package com.sujeet.nasaimageoftheday.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/*** Image Response ***/
@JsonClass(generateAdapter = true)
data class ImageResponse(
    @Json(name = "copyright")
    val copyright: String? = null,
    @Json(name = "date")
    val date: String? = null,
    @Json(name = "explanation")
    val explanation: String? = null,
    @Json(name = "hdurl")
    val hdurl: String? = null,
    @Json(name = "media_type")
    val media_type: String? = null,
    @Json(name = "service_version")
    val service_version: String? = null,
    @Json(name = "title")
    val title: String? = null,
    @Json(name = "url")
    val url: String? = null
)