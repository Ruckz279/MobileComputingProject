package com.example.dailydiet.addinfo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dailydiet.R
import com.example.dailydiet.SaveSharedPref
import kotlinx.android.synthetic.main.activity_height.*
import kotlinx.android.synthetic.main.activity_weight.*

class WeightActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight)
        next3.setOnClickListener(){
            val weight = weight.text.toString()
            if (weight.toInt() >0) {
                saveAge(weight)
                val intent = Intent(this, GenderActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun saveAge( weight:String){
        val sharedPref= SaveSharedPref()
        val editor = sharedPref.saveStringItem("weight",weight,this)
    }


}
