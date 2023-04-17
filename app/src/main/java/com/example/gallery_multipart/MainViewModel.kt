package com.example.gallery_multipart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gallery_multipart.data.ImageModel

class MainViewModel : ViewModel() {
    private val _pets = MutableLiveData<List<ImageModel>>()
    val pets: LiveData<List<ImageModel>> get() = _pets

    fun submitPet() {
        Log.d("bbb", "서밋펫 접근 성공")
    }
}