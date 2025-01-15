package com.example.cvs.flickr.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cvs.flickr.api.data.FlickrImagesRepository
import com.example.cvs.flickr.api.model.FlickrImagesData
import com.example.cvs.flickr.api.network.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlickrImagesViewModel @Inject internal constructor (
    private val flickrImagesRepository: FlickrImagesRepository
) : ViewModel() {
    private val _imagesData = MutableLiveData<FlickrImagesData>()
    val imagesData: MutableLiveData<FlickrImagesData> = _imagesData
    private val _imagesScreenUpdate = MutableStateFlow(false)
    val imageScreenUpdate: MutableStateFlow<Boolean> = _imagesScreenUpdate
    private val _error = MutableStateFlow(false)
    val error: MutableStateFlow<Boolean> = _error

    fun getFlickrImages(imageName: String) {
        viewModelScope.launch {
            when (val result : ResultState = flickrImagesRepository.getFlickrImages(imageName)) {
                is ResultState.Success -> {
                    _imagesData.value = result.data as FlickrImagesData
                    _imagesScreenUpdate.value = true
                }
                is ResultState.Error -> {
                    _imagesScreenUpdate.value = false
                    _error.value = true
                }
                else -> {}
            }
        }
    }
}