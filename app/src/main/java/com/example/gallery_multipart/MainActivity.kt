package com.example.gallery_multipart

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.gallery_multipart.data.ImageModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val originList: ArrayList<ImageModel> = ArrayList()
        originList.add(ImageModel(null))
        originList.add(ImageModel(null))
        originList.add(ImageModel(null))
        originList.add(ImageModel(null))
        originList.add(ImageModel(null))

        val listAdapter = MainListAdapter(this, originList)
        this.findViewById<ListView>(R.id.lvImage).adapter = listAdapter
    }
}

class MainListAdapter(val context: Context, val list: ArrayList<ImageModel>): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        /* LayoutInflater는 item을 Adapter에서 사용할 View로 부풀려주는(inflate) 역할을 한다. */
        val view: View = LayoutInflater.from(context).inflate(R.layout.row_image_list_view, null)

        /* 위에서 생성된 view를 res-layout-main_lv_item.xml 파일의 각 View와 연결하는 과정이다. */
        val rowImage = view.findViewById<ImageView>(R.id.rowImage)
        val rowTvImageName = view.findViewById<TextView>(R.id.rowTvImageName)
        val rowBtnSelectImage = view.findViewById<Button>(R.id.rowBtnSelectImage)

        /* ArrayList<Dog>의 변수 dog의 이미지와 데이터를 ImageView와 TextView에 담는다. */
        val model = list[position]
        rowTvImageName.text = model.imageUrl

        return view
    }

    override fun getCount(): Int {
        return list.size;
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}