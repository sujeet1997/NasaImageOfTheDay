package com.sujeet.nasaimageoftheday.network.repository

import android.util.Log
import android.widget.Toast
import com.sujeet.nasaimageoftheday.ApplicationClass
import com.sujeet.nasaimageoftheday.network.Internet
import retrofit2.Response


open class BaseRepository {

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, error: String): T? {

        return when (val result = apiOutput(call, error)) {
                // return API response
                is Output.Success -> result.output
                // return error msg
                is Output.Error -> {
                    // print error
                    Log.e("Error", "The $error and the ${result.exception}")
                    // return null
                    null
                }
        }
    }

//    suspend fun <T : Any> safeApiCall(call: suspend () -> Output<T>, error: String): Output<T> {
//
//        return when (val result = call.invoke()) {
//            is Output.Success -> result
//            is Output.Error -> {
//                Log.e("Error", "The $error and the ${result.exception}")
//                Output.Error(result.exception)
//            }
//        }
//    }

    private suspend fun <T : Any> apiOutput(
        call: suspend () -> Response<T>,
        error: String
    ): Output<T> {

        // check internet connection before API call
        return if (Internet.isNetworkConnected()) {

            // call the network API request
            val response = call.invoke()

            // 200 -> Ok
            return if (response.isSuccessful) {

                if (response.body() != null) {
                    Output.Success(response.body()!!)

                }
                else
                    Output.Error(Exception(response.errorBody().toString()))

            } else {
                if(ApplicationClass.mCurrentActivity != null) {
                    ApplicationClass.mCurrentActivity!!.runOnUiThread(Runnable {
                        Toast.makeText(ApplicationClass.mCurrentActivity, "Something went wrong try again later !! \n code : ${response.code()}", Toast.LENGTH_LONG).show()
                    })
                }

                Output.Error(Exception("OOps ... Something went wrong due to  ${response.code()} : ${response.raw()}"))
            }

        } else {

            if(ApplicationClass.mCurrentActivity != null) {
                ApplicationClass.mCurrentActivity!!.runOnUiThread(Runnable {
                    Toast.makeText(ApplicationClass.mCurrentActivity, "Check Internet Connection !!!", Toast.LENGTH_LONG).show()
                })
            }

            Output.Error(Exception("OOps ... Check Internet connection"))
        }

    }


}


