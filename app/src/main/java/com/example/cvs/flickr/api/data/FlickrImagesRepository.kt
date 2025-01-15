package com.example.cvs.flickr.api.data

import com.example.cvs.flickr.api.model.FlickrImagesData
import com.example.cvs.flickr.api.network.ApiService
import com.example.cvs.flickr.api.network.ResultState

interface FlickrImagesRepository {
    suspend fun getFlickrImages(imageName: String): ResultState
    fun getImageData() : FlickrImagesData?
}

class FlickrImagesRepositoryImpl(private val apiService: ApiService
) : FlickrImagesRepository {
    private var imagesData: FlickrImagesData? = null
    override suspend fun getFlickrImages(imageName: String): ResultState {
        try {
            val response = apiService.getFlickrImages(imageName = imageName)
            if (response.items.isNotEmpty()) {
                imagesData = response
                return ResultState.Success(response)
            }
            return ResultState.Error("Images not found")
        } catch (e : Exception) {
            return ResultState.Error("Images not found")
        }
    }

    override fun getImageData() : FlickrImagesData? {
        return imagesData
    }

}