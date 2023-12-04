package com.example.mediastore_exifinterface_example.ui.screens.editor

data class EditorUiState(
    val date: String? = "",
    val latitude: String? = "",
    val longitude: String? = "",
    val phoneName: String? = "",
    val phoneModel: String? = "",
)

data class EditorCheckBoxUiState(
    val date: Boolean = false,
    val location: Boolean = false,
    val phoneName: Boolean = false,
    val phoneModel: Boolean = false,
)
