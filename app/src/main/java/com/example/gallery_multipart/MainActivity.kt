package com.example.gallery_multipart

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.viewModels
import com.example.gallery_multipart.base.BindingActivity
import com.example.gallery_multipart.data.ImageModel
import com.example.gallery_multipart.databinding.ActivityMainBinding

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private lateinit var rvAdapter: MainListAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // petRegisterViewModel.getPets()
        initAdapter()
        clickEvents()
        // clickBack()
        initPets()

        rvAdapter.addNewForm()
    }

    private fun initAdapter() {
        rvAdapter = MainListAdapter(this)
        binding.rvImage.adapter = rvAdapter
        binding.rvImage.setHasFixedSize(true)
    }

    private fun initPets() {
        viewModel.pets.observe(this) {
            rvAdapter.setRegisteredPetlist(it)
        }
    }

    private fun clickEvents() {
        binding.btnAddRow.setOnClickListener {
            rvAdapter.addNewForm()
        }
    }
}