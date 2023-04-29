package com.example.gallery_multipart

import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import com.example.gallery_multipart.base.BindingActivity
import com.example.gallery_multipart.data.Pet
import com.example.gallery_multipart.databinding.ActivityMainBinding

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    // data : MainViewModel의 pets의 복사본.
    var data = MutableLiveData<ArrayList<Pet>>()
    // rvAdapter : RecyclerView Adapter
    private lateinit var rvAdapter: MainListAdapter
    // ViewModel
    private val viewModel: MainViewModel by viewModels()

    // selectedPosition : getContent를 쓸 때 어느 위치에 이미지를 넣어야 하는지 알아야 해서, 몇 번째 셀이 눌렸는지를 임시로 저장하는 변수.
    var selectedPosition: Int = -1
    // getContent : 갤러리에서 이미지를 선택오는 부분
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        // 1. list라는 변수에 data에 있던 값을 복사해서 가져옴.
        val list = data.value!!

        // 2. selectedPosition에 해당하는 위치의 imageUrl, name을 변경함
        list[selectedPosition].imageUrl = uri
        list[selectedPosition].name = uri?.path ?: ("Image$selectedPosition")

        // 3. ViewModel에 있는 pets에 2에서 만든 list를 다시 넣어줌.
        //    -> 아래에 있는 viewModel.pets.observe 안의 코드가 다시 실행되고, 갱신된 값으로 adapter가 새로 생긴다.
        viewModel.pets.postValue(list)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewModel에 있는 pets에 대한 observer 등록
        initPets()

        // 'ADD ROW' 버튼 이벤트 등록
        clickEvents()
    }

    private fun initPets() {
        // viewModel의 pets가 바뀔 때 다음의 동작을 한다 : pets에 변경이 생길 때마다 adapter를 새로 생성한다.
        viewModel.pets.observe(this) {
            // 1. data에 pets의 값을 그대로 복사해서 넣음.
            data.value = it

            // 2. 1에서 넣은 data를 가지고 MainListAdapter를 다시 생성함.
            rvAdapter = MainListAdapter(data)

            // 3. 2에서 만든 adapter의 onItemClickListner 새로 등록함.
            rvAdapter.setOnImageClickListener  { pos: Int ->
                selectedPosition = pos
                getContent.launch("image/*")
            }

            // 4. RecyclerView에 adapter 등록
            binding.rvImage.adapter = rvAdapter
        }
    }

    private fun clickEvents() {
        // 'ADD ROW' 버튼에 대한 이벤트
        binding.btnAddRow.setOnClickListener {
            // RecyclerView의 아이템 갯수가 4개 미만이면 : 비어 있는 Pet 데이터 하나 추가
            if(data.value != null && data.value!!.size < 4) {
                // 1. list라는 변수에 data에 있던 값을 복사해서 가져옴.
                val list = data.value!!
                // 2. 1에서 복사한 list에 비어 있는 Pet 데이터 하나 추가.
                list.add(Pet())
                // 3. ViewModel에 있는 pets에 2에서 만든 list를 다시 넣어줌.
                //    -> 아래에 있는 viewModel.pets.observe 안의 코드가 다시 실행되고, 갱신된 값으로 adapter가 새로 생긴다.
                viewModel.pets.postValue(list)
            }

            // 만약 초기화가 잘못되었거나 등의 문제로 data가 null일 경우 : 비어 있는 Pet 데이터 하나 넣어줌
            else if(data.value == null) {
                val list = ArrayList<Pet>()
                list.add(Pet())
                viewModel.pets.postValue(list)
            }

            // RecyclerView의 아이템 갯수가 4개 이상이면 : 아무 것도 안 함
        }
    }
}