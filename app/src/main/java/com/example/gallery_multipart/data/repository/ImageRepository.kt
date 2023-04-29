package com.example.gallery_multipart.data.repository

import android.graphics.Bitmap
import com.example.gallery_multipart.data.remote.RemoteImageSource
import com.google.gson.Gson
import retrofit2.Response
import java.io.*
import java.util.*

interface ImageRepository {
    suspend fun postImages(sideImg: Bitmap, backImg: Bitmap, gaugeImg: Bitmap, gaugeKm: String, token: String): ApiResponse<AdMissionPost>
}

class ImageRepositoryImpl(private val remoteImageSource: RemoteImageSource) : ImageRepository {
    override suspend fun postImages(sideImg: Bitmap, backImg: Bitmap, gaugeImg: Bitmap, gaugeKm: String, token: String): ApiResponse<AdMissionPost> {
        val apiResult: Response<String> = remoteImageSource.postImages(convertBitmapToFile(sideImg, "1"), convertBitmapToFile(backImg, "2"), convertBitmapToFile(gaugeImg, "3"), gaugeKm, "Bearer "+token)

        return if (apiResult.code() == 200) {
            ApiResponse(true, Gson().fromJson(apiResult.body()!!, AdMissionPostData::class.java).data, null)
        } else {
            ApiResponse(false, null, makeErrorResponseFromStatusCode(apiResult.code(), "/ad/mission"))
        }
    }

    private fun convertBitmapToFile(bitmap: Bitmap, name: String): File {
        //create a file to write bitmap data
        //val file = File(App().context().cacheDir, "FILE_"+ SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())+"_")
        val file = File(App().context().cacheDir, name)
        file.createNewFile()

        //Convert bitmap to byte array
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70 /*ignored for PNG*/, bos)
        val bitMapData = bos.toByteArray()

        //write the bytes in file
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        try {
            fos?.write(bitMapData)
            fos?.flush()
            fos?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }
}