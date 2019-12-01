package com.example.dailydiet.addinfo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dailydiet.R
import com.example.dailydiet.SaveSharedPrefHelper
import kotlinx.android.synthetic.main.activity_height.*

class HeightActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_height)
        next2.setOnClickListener(){
            val height = height.text.toString()
            //check if height entered
            if (height.isNotEmpty() && height.toInt() >0) {
                saveAge(height)
                val intent = Intent(this, WeightActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun saveAge( height:String){
        val sharedPref= SaveSharedPrefHelper()
        val editor = sharedPref.saveStringItem("height",height,this)
    }


}
