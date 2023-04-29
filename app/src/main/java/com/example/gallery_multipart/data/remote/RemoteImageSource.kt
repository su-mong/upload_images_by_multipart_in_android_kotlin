package com.example.gallery_multipart.data.remote

import com.example.gallery_multipart.data.service.Api
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File


interface RemoteImageSource {
    suspend fun postImages(side_image: File, back_image: File, instrument_panel_image: File, travelled_distance: String, authorization: String): Response<String>
}

class RemoteImageSourceImpl(private val service: Api) : RemoteImageSource {
    override suspend fun postImages(side_image: File, back_image: File, instrument_panel_image: File, travelled_distance: String, authorization: String): Response<String> {
        val sideImgFile = side_image.asRequestBody("image/*".toMediaTypeOrNull())
        val sideImgFileBody: MultipartBody.Part = createFormData("side_image", side_image.name+".jpg", sideImgFile)

        val backImgFile = back_image.asRequestBody("image/*".toMediaTypeOrNull())
        val backImgFileBody: MultipartBody.Part = createFormData("back_image", back_image.name+".jpg", backImgFile)

        val panelImgFile = instrument_panel_image.asRequestBody("image/*".toMediaTypeOrNull())
        val panelImgFileBody: MultipartBody.Part = createFormData("instrument_panel_image", instrument_panel_image.name+".jpg", panelImgFile)

        val distanceStr = travelled_distance.toRequestBody("text/plain".toMediaTypeOrNull())

        return service.postImages(sideImgFileBody, backImgFileBody, panelImgFileBody, distanceStr, authorization)
    }
}