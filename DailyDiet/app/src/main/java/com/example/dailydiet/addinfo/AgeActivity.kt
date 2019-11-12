package com.example.dailydiet.addinfo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.dailydiet.R
import com.example.dailydiet.SaveSharedPref
import kotlinx.android.synthetic.main.activity_age.*


class AgeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_age)

        next.setOnClickListener(){
            val age = age.text.toString()
            if (age.toInt() >0) {
                saveAge(age)
                val intent = Intent(this, HeightActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun saveAge( age:String){
        val sharedPref= SaveSharedPref()
        val editor = sharedPref.saveStringItem("age",age,this)
    }


}
