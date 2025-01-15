package com.example.cvs.flickr.ui.compose

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.cvs.flickr.R
import com.example.cvs.flickr.api.data.DefaultAppContainer
import com.example.cvs.flickr.api.model.ImageData
import com.example.cvs.flickr.api.model.Media
import com.example.cvs.flickr.utils.DateUtils
import com.example.cvs.flickr.viewmodel.FlickrImagesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlickrScreen(
    navController: NavController
) {
    val imageName: MutableState<String> = remember { mutableStateOf("") }
    val appContainer = DefaultAppContainer()
    val viewModel = FlickrImagesViewModel(appContainer.flickrImagesRepository)
    val flickrImagesData = viewModel.imagesData
    val displayImagesScreen = viewModel.imageScreenUpdate.collectAsState()
    val focusManager = LocalFocusManager.current
    val imageData: MutableState<ImageData> = remember {
        mutableStateOf(ImageData("", "", Media(""),"","","","","",""))
    }
    val displayImageData = remember { mutableStateOf(false) }
    val displayError = viewModel.error.collectAsState()

    Scaffold { it -> Box { Modifier.padding(it)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 88.dp)
        ) {
            TextField(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
                readOnly = false,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(20.dp),
                value = imageName.value,
                singleLine = true,
                label = { Text (stringResource(id = R.string.search_image)) },
                onValueChange = {
                    imageName.value = it
                },
                trailingIcon = {
                    IconButton(onClick = {
                        if (imageName.value.isNotEmpty()) {
                            viewModel.getFlickrImages(imageName.value)
                            focusManager.clearFocus()
                        }
                    }) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = ""
                        )
                    }
                }
            )
            if (displayImagesScreen.value) {
                flickrImagesData.value?.items.let { it1 ->
                    it1?.let { it2 ->
                        FlickrImagesListScreen(flickrImagesList = it2) {
                            imageData.value = it
                            displayImageData.value = true
                        }
                    }
                }
            }
        }
        if (displayImageData.value) {
            DisplayImageData(displayImageData, imageData)
        }
        if (displayError.value) {
            DisplayToastMessage()
        }
    }
    }
}

@Composable
fun FlickrImagesListScreen(
    flickrImagesList: List<ImageData>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onImageClick: (ImageData) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(modifier = modifier.padding(top = 10.dp,
            start = 20.dp, end = 20.dp),
            contentPadding = contentPadding
        ) {
            items(
                count = flickrImagesList.size,
                key = {
                    it
                },
                itemContent = {
                    ImageCard(it, flickrImagesList) { imageData ->
                        onImageClick(imageData)
                    }
                }
            )
        }
    }
}

@Composable
fun ImageCard (i: Int,
                flickrImagesList: List<ImageData>,
                onImageClick:(ImageData) -> Unit) {
    Card(
        modifier = Modifier,
        onClick = {
            onImageClick(flickrImagesList[i])
        }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
               modifier = Modifier.background(Color.LightGray)) {
            Text(
                text = flickrImagesList[i].title,
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp, start = 10.dp)
                    .fillMaxWidth()
                    .background(Color.Transparent)
            )
        }
    }
}

@Composable
fun DisplayImageData(
    displayImage: MutableState<Boolean>,
    imageData: MutableState<ImageData>
) {
    if (displayImage.value) {
        AlertDialog(
            onDismissRequest = {
                displayImage.value = false
            },
            title = {},
            text = {
                Column {
                    Image(
                        painter = rememberAsyncImagePainter(imageData.value.media.m),
                        contentDescription = null,
                        modifier = Modifier.size(256.dp)
                    )
                    Text(
                        text = stringResource(R.string.title, imageData.value.title))
                    Text(modifier = Modifier.padding(top = 10.dp),
                        text = stringResource(R.string.description, imageData.value.tags))
                    Text(modifier = Modifier.padding(top = 10.dp),
                        text = stringResource(R.string.author, imageData.value.author))
                    Text(modifier = Modifier.padding(top = 10.dp),
                        text = stringResource(R.string.published, DateUtils.formatDate(imageData.value.published)))
                }
            },
            confirmButton = {},
            dismissButton = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            displayImage.value = false
                        }
                    ) {
                        Text(stringResource(id = R.string.close))
                    }
                }
            }
        )
    }
}

@Composable
fun DisplayToastMessage() {
    Toast.makeText(
        LocalContext.current, stringResource(id = R.string.no_images_found),
        Toast.LENGTH_SHORT
    ).show()
}