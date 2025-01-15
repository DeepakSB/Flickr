package com.example.cvs.flickr.api.data

import com.example.cvs.flickr.api.network.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val flickrImagesRepository: FlickrImagesRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://api.flickr.com/services/feeds/"
    private val okHttp = OkHttpClient.Builder().build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(okHttp)
        .build()

    /**
     * Retrofit service object for creating api calls
     */
    private val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    override val flickrImagesRepository: FlickrImagesRepository by lazy {
        FlickrImagesRepositoryImpl(retrofitService)
    }
}