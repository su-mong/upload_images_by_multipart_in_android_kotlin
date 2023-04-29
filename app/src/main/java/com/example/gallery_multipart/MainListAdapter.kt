package com.example.gallery_multipart

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.gallery_multipart.data.ImageModel
import com.example.gallery_multipart.databinding.RowImageListViewBinding

class MainListAdapter(var petRegisterList: LiveData<ArrayList<ImageModel>>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), MypagePetRegisterFormListener {
    /// MainListAdapter의 요소에 접근 가능하도록 하기 위해 inner class로 변경
    inner class MainListViewHolder(
        private val binding: RowImageListViewBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ImageModel, cancel: (Int) -> Unit) {
            binding.position = position;

            binding.apply {
                this.position = position
                listener = this@MainListAdapter
                //binding.rowImage.setOnClickListener {
                    //getContent.launch("image/*")
                //}
                binding.rowImage.setOnClickListener {
                    onItemClickListener?.let {
                        it(position ?: 0)
                    }
                }
                binding.rowImage.load(
                    data.imageUrl ?: R.drawable.ic_launcher_foreground
                )
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

    private fun getImage(uri: Uri?, position: Int) {
        petRegisterList.value!![position].imageUrl = uri
        petRegisterList.value!![position].name = uri?.path ?: ("Image$position")
        notifyDataSetChanged()
    }




    /// https://developer88.tistory.com/356
    private var onItemClickListener: ((Int) -> Unit)? = null
    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }
}