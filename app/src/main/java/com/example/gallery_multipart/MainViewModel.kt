package com.example.gallery_multipart

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gallery_multipart.data.Pet

class MainViewModel : ViewModel() {
    // List -> ArrayList
    val pets = MutableLiveData<ArrayList<Pet>>()

    // ViewModel 생성할 때 pets에 Pet Model 1개를 추가한다.
    // pets 목록을 불러와야 한다면 여기서 불러온다.(API Call을 여기서 한다는 의미)
    init{
        val _initData = ArrayList<Pet>()
        _initData.add(Pet())
        pets.postValue(_initData)
    }

    // 이미지 전송을 포함한 API Call.
    fun submitPet() {
        Log.d("bbb", "서밋펫 접근 성공")
    }
}