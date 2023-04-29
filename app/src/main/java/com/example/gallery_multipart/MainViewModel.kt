package com.example.gallery_multipart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gallery_multipart.data.ImageModel

class MainViewModel : ViewModel() {
    val pets = MutableLiveData<ArrayList<ImageModel>>()

    init{
        var userData = ArrayList<ImageModel>()
        userData.add(ImageModel())
        pets.postValue(userData)
    }

    fun submitPet() {
        Log.d("bbb", "서밋펫 접근 성공")
    }
}