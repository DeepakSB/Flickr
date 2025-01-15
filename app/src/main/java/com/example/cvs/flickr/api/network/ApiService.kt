package com.example.cvs.flickr.api.network

import com.example.cvs.flickr.api.model.FlickrImagesData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("photos_public.gne")
    suspend fun getFlickrImages(@Query("format") format: String = "json",
                                @Query("nojsoncallback") noJsonCb: Int = 1,
                                @Query("tags") imageName: String): FlickrImagesData


}