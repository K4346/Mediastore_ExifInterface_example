package com.example.mediastore_exifinterface_example.ui.screens.picture

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel

class PictureViewModel(application: Application) : AndroidViewModel(application) {
    private var _imageUri: MutableState<Uri?> = mutableStateOf(null)
    var imageUri: State<Uri?> = _imageUri
    lateinit var encodedUri: String

    fun setImage(uri: Uri) {
        _imageUri.value = uri
    }
}