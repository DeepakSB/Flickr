package com.example.cvs.flickr.api.model

data class FlickrImagesData (val title: String, val link: String,
                             val description: String, val modified: String,
                             val generator: String, val items: List<ImageData>)


data class ImageData(val title: String, val link: String,
                     val media: Media, val date_taken: String,
                     val description: String, val published: String,
                     val author: String, val author_id: String,
                     val tags: String)

data class Media(val m: String)