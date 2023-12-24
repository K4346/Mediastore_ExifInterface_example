package com.example.mediastore_exifinterface_example.ui.screens.picture

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.exifinterface.media.ExifInterface
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mediastore_exifinterface_example.AppRoutes
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun PictureScreen(vm: PictureViewModel = viewModel(), navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val context = LocalContext.current
        val imageUri = vm.imageUri
        val pickImageLauncher =
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult()
            )
            { result ->
                if (result.resultCode == Activity.RESULT_OK && result.data != null && result.data!!.data != null) {
                    val uri = result.data!!.data!!
                    vm.setImage(uri)
                    Log.i("kpop", uri.toString())
                    val encodedUri =
                        URLEncoder.encode(uri.toString(), StandardCharsets.UTF_8.toString())
                    vm.encodedUri = encodedUri
                }
            }
        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.type = "image/*";
                pickImageLauncher.launch(intent)

            },
            Modifier.padding(top = 10.dp)
        ) {
            Text("Выбрать изображение")
        }
        if (imageUri.value != null) {
            AsyncImage(
                model = imageUri.value,
                contentDescription = null,
            )


            val contentResolver = context.contentResolver
            contentResolver.openFileDescriptor(imageUri.value!!, "rw", null)
                ?.use { fileDescriptor ->
                    val exif = ExifInterface(fileDescriptor.fileDescriptor)


                    exif.getAttribute(ExifInterface.TAG_DATETIME)?.let {
                        Text("Дата: $it")
                    }
                    exif.getAttribute(ExifInterface.TAG_MAKE)?.let {
                        Text("Устройство: $it")
                    }
                    exif.getAttribute(ExifInterface.TAG_MODEL)?.let {
                        Text("Модель: $it")
                    }
                    exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE)?.let {
                        Text("Широта: ${vm.getLocationFromAttr(it)}")
                    }
                    exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE)?.let {
                        Text("Долгота: ${vm.getLocationFromAttr(it)}")
                    }
                }
            Button(onClick = {
                navController.navigate("${AppRoutes.EditorScreen.name}/${vm.encodedUri}")
            }) {
                Text(text = "Редактировать теги")
            }
        }
    }
}
