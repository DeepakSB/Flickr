package com.example.cvs.flickr.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateUtils {

    companion object {

        fun formatDate(
            inputDateString: String?,
        ): String {
            val date: Date?
            val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val outputDateFormat = SimpleDateFormat("MMM dd yyyy", Locale.getDefault())
            var outputDateString = ""
            try {
                date = inputDateString?.let { inputDateFormat.parse(it) }
                outputDateString = date?.let { outputDateFormat.format(it) }.toString()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return outputDateString
        }
    }

}