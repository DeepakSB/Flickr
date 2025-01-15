package com.example.cvs.flickr

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FlickrImagesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}