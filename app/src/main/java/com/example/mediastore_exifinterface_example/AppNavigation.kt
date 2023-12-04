package com.example.mediastore_exifinterface_example

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mediastore_exifinterface_example.ui.screens.editor.EditorScreen
import com.example.mediastore_exifinterface_example.ui.screens.picture.PictureScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.PictureScreen.name
    ) {

        composable(AppRoutes.PictureScreen.name) {
            PictureScreen(navController = navController)
        }
        composable("${AppRoutes.EditorScreen.name}/{uriPath}") { backStackEntry ->

            val uriPath = backStackEntry.arguments?.getString("uriPath").toString()
            val uri = Uri.parse(uriPath)
            EditorScreen(uri = uri, navController = navController)
        }
    }
}

enum class AppRoutes {
    PictureScreen, EditorScreen
}