package com.example.dailydiet.addinfo


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dailydiet.R
import com.example.dailydiet.SaveSharedPrefHelper
import kotlinx.android.synthetic.main.activity_age.*


class AgeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_age)
        next.setOnClickListener(){
            val age = age.text.toString()
            //check if age entered
            if (age.isNotEmpty() && age.toInt() >0) {
                saveAge(age)
                //push height activity
                val intent = Intent(this, HeightActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun saveAge( age:String){
        val sharedPref= SaveSharedPrefHelper()
        val editor = sharedPref.saveStringItem("age",age,this)
    }


}
