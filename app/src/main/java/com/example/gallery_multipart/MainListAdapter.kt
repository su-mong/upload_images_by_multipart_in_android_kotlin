package com.example.gallery_multipart

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.gallery_multipart.data.Pet
import com.example.gallery_multipart.databinding.RowImageListViewBinding

class MainListAdapter(var petRegisterList: LiveData<ArrayList<Pet>>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), MypagePetRegisterFormListener {

    // MainListAdapter의 요소에 접근 가능하도록 하기 위해 inner class로 변경
    inner class MainListViewHolder(
        private val binding: RowImageListViewBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Pet, cancel: (Int) -> Unit) {
            // binding에 있는 position 값 초기화
            binding.position = position

            binding.apply {
                // binding에 있는 position 값 초기화
                this.position = position
                // binding에 있는 listener 값 초기화
                listener = this@MainListAdapter

                // 이미지뷰 클릭 이벤트
                binding.rowImage.setOnClickListener {
                    // 아래에서 만든 onImageClickListener(MainActivity에서 선언한)를 실행함.
                    onImageClickListener?.let {
                        // 넘겨줘야 하는 값(여기서는 position)을 넣어줌.
                        // position이 nullable이기 때문에, 만에 하나 null이면 임시로 0을 넘겨줌.
                        it(position ?: 0)
                    }
                }

                // 이미지뷰의 이미지 로딩(기존과 같음)
                binding.rowImage.load(
                    data.imageUrl ?: R.drawable.ic_launcher_foreground
                )

                // 텍스트뷰의 텍스트 로딩(기존과 같음)
                binding.rowTvImageName.text = data.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            RowImageListViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MainListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is MainListViewHolder) {
            holder.onBind(petRegisterList.value!![position]) { onCancel(it) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun getItemCount(): Int {
        return petRegisterList.value!!.size
    }


    override fun onCancel(position: Int) {
        removePetRegisterForm(position)
    }

    private fun removePetRegisterForm(position: Int) {
        val countToRegisterViewHolder = petRegisterList.value!!.size
        if (countToRegisterViewHolder == 0) return
        petRegisterList.value!!.removeAt(position)
        notifyDataSetChanged()
    }


    // 시도했던 것 : onCancel Interface가 있으니,
    //            이미지를 추가하는 onAddImage도 만들 수 있겠다고 생각함.
    //            (onImageClickListener를 onAddImage로 변경할 수 있음)
    //
    // 참고 : https://developer88.tistory.com/356
    private var onImageClickListener: ((Int) -> Unit)? = null
    fun setOnImageClickListener(listener: (Int) -> Unit) {
        onImageClickListener = listener
    }
}