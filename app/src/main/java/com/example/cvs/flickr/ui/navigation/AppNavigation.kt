package com.example.cvs.flickr.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cvs.flickr.ui.compose.FlickrScreen
import com.example.cvs.flickr.ui.theme.FlickrTheme
import com.example.cvs.flickr.utils.Constants


@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()

) {

    FlickrTheme {
        NavHost(
            navController = navController,
            startDestination = Constants.FLICKR_SCREEN
        ) {
            composable(route = Constants.FLICKR_SCREEN) {
                FlickrScreen(navController)
            }
        }
    }
}