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
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.gallery_multipart.data.ImageModel
import com.example.gallery_multipart.databinding.RowImageListViewBinding

class MainListAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), MypagePetRegisterFormListener {
    var petRegisterList = mutableListOf<ImageModel>()
    val getContent = ((context as AppCompatActivity).registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        getImage(uri, 0)
    })

    /// MainListAdapter의 요소에 접근 가능하도록 하기 위해 inner class로 변경
    inner class MainListViewHolder(
        private val binding: RowImageListViewBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ImageModel, cancel: (Int) -> Unit) {
            binding.apply {
                this.position = position
                listener = this@MainListAdapter
                binding.rowImage.setOnClickListener {
                    getContent.launch("image/*")
                }
                binding.rowImage.load(
                    data.imageUrl ?: R.drawable.ic_launcher_foreground
                )
                binding.rowTvImageName.text = data.name

                Log.d("getCCC", "" + data.imageUrl?.path)
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
            holder.onBind(petRegisterList[position]) { onCancel(it) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun getItemCount(): Int {
        if (petRegisterList.size == 4) {
            return petRegisterList.size
        }
        return petRegisterList.size + 1
    }



    fun setRegisteredPetlist(pet: List<ImageModel>) {
        petRegisterList.removeAll(pet)
        petRegisterList.addAll(pet)
        notifyDataSetChanged()
    }

    fun addNewForm() {
        if (petRegisterList.size == 4) return
        petRegisterList.add(ImageModel())
        Log.d("getCCC", (petRegisterList.size).toString())
        notifyDataSetChanged()
    }

    override fun onCancel(position: Int) {
        removePetRegisterForm(position)
    }

    private fun removePetRegisterForm(position: Int) {
        val countToRegisterViewHolder = petRegisterList.size
        if (countToRegisterViewHolder == 0) return
        petRegisterList.removeAt(position)
        notifyDataSetChanged()
    }

    private fun getImage(uri: Uri?, position: Int) {
        petRegisterList[position].imageUrl = uri
        petRegisterList[position].name = uri?.path ?: ("Image$position")
        notifyDataSetChanged()
    }
}