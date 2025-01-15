package com.example.cvs.flickr

import com.example.cvs.flickr.utils.DateUtils
import org.junit.Test

import org.junit.Assert.*

class DateUtilsTest {

    @Test
    fun test_DateUtils_1() {
        val result = DateUtils.formatDate("")
        assertTrue(result.isEmpty())
    }

    @Test
    fun test_DateUtils_2() {
        val result = DateUtils.formatDate("2025-01-08T01:24:14Z")
        assertTrue(result == "Jan 08 2025")
    }

    @Test
    fun test_DateUtils_3() {
        val result = DateUtils.formatDate("2025-01-08T01:24:14")
        assertTrue(result.isEmpty())
    }

    @Test
    fun test_DateUtils_4() {
        val result = DateUtils.formatDate("2025-01-08")
        assertTrue(result.isEmpty())
    }

    @Test
    fun test_DateUtils_5() {
        val result = DateUtils.formatDate("2025-01")
        assertTrue(result.isEmpty())
    }

}