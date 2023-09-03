package com.sujeet.nasaimageoftheday.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sujeet.nasaimageoftheday.baseClass.BaseViewModel
import com.sujeet.nasaimageoftheday.model.ImageResponse
import kotlinx.coroutines.launch

class DashboardViewModel:BaseViewModel() {

    /*** Image Api Call viewModel ***/
    private val _imageLiveData = MutableLiveData<ImageResponse>()
    val imageLiveData: LiveData<ImageResponse> = _imageLiveData

    fun getImageData(apiKey: String, date: String) {
        scope.launch {
            _imageLiveData.postValue(apiRepository.getImageDatas(apiKey, date))
        }
    }
}