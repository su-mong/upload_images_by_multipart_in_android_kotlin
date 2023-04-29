package com.example.gallery_multipart

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.gallery_multipart.base.BindingActivity
import com.example.gallery_multipart.data.ImageModel
import com.example.gallery_multipart.databinding.ActivityMainBinding

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    var data = MutableLiveData<ArrayList<ImageModel>>()
    private lateinit var rvAdapter: MainListAdapter
    private val viewModel: MainViewModel by viewModels()

    var selectedPosition: Int = -1
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        val list = data.value!!
        list[selectedPosition].imageUrl = uri
        list[selectedPosition].name = uri?.path ?: ("Image$selectedPosition")
        viewModel.pets.postValue(list)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPets()
        clickEvents()
    }

    private fun initPets() {
        viewModel.pets.observe(this) {
            data.value = it

            rvAdapter = MainListAdapter(data)
            rvAdapter.setOnItemClickListener  { pos: Int ->
                selectedPosition = pos
                getContent.launch("image/*")
            }
            binding.rvImage.adapter = rvAdapter
        }
    }

    private fun clickEvents() {
        binding.btnAddRow.setOnClickListener {
            if(data.value != null && data.value!!.size < 4) {
                val list = data.value!!
                list.add(ImageModel())
                viewModel.pets.postValue(list)
            } else if(data.value == null) {
                val list = ArrayList<ImageModel>()
                list.add(ImageModel())
                viewModel.pets.postValue(list)
            }
        }
    }
}