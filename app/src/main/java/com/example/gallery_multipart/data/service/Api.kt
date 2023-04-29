package com.example.gallery_multipart.data.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface Api {
    @Multipart
    @POST("/accounts/FW25bFt/uploads/form_data")
    suspend fun postImages(
        @Part side_image: MultipartBody.Part,
        @Part back_image: MultipartBody.Part,
        @Part instrument_panel_image: MultipartBody.Part,
        @Part("travelled_distance") travelled_distance: RequestBody,
        @Header("Authorization") authorization: String
    ): Response<String>
}