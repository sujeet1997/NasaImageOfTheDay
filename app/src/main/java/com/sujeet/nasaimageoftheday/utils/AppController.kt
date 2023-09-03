package com.sujeet.nasaimageoftheday.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi


object AppController {

    fun dateAPIFormats(reqDate: String?): String? {
        if (reqDate != null && !reqDate.isEmpty()) {
            val dateFor: String = reqDate
            return dateFor.split("-".toRegex())
                    .toTypedArray()[2] + "/" + dateFor.split("-".toRegex())
                    .toTypedArray()[1] + "/" + dateFor.split("-".toRegex()).toTypedArray()[0]
        }
        return reqDate
    }


    /*** Minimum Internet Speed 16 kbps for Scanning & Upload Code ***/
    @RequiresApi(api = Build.VERSION_CODES.M)
    fun isThereInternetSpeed(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        @SuppressLint("MissingPermission") val activeNetworkInfo = connectivityManager.activeNetworkInfo
        var nc: NetworkCapabilities? = null
        //should check null because in airplane mode it will be null
        if (activeNetworkInfo != null) {
            nc = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            val downSpeed = nc!!.linkDownstreamBandwidthKbps
            val upSpeed = nc.linkUpstreamBandwidthKbps
            Log.d("Internet Speed", "Download Speed : $downSpeed, Upload Speed : $upSpeed")
            if (downSpeed > 16 && upSpeed > 16) return true
        }
        Log.d("ebfiewf", "fejwfinef")
        return false
    }


}