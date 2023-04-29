package com.example.gallery_multipart.data

import android.net.Uri

data class Pet(
    var imageUrl: Uri? = null,
    var name: String = "nothing!",
)