package com.example.dailydiet.addinfo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dailydiet.R
import com.example.dailydiet.SaveSharedPrefHelper
import kotlinx.android.synthetic.main.activity_gender.*

class GenderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        var gender:String = ""
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gender)
        next4.setOnClickListener() {
            if (gender == "1") {
                saveGender("MALE")
            }
            else if (gender == "2") {
                saveGender("FEMALE")
            }
            if(gender.isNotEmpty()) {
                val intent = Intent(this, ActiveActivity::class.java)
                startActivity(intent)
            }
        }

        // using toggle button to select gender
        maleToggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // The toggle is enabled
                femaleToggle.isChecked = false
                gender = "1"
            }
        }
        // Set an checked change listener for toggle button
        femaleToggle.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                gender ="2"
                maleToggle.isChecked = false
            }
        }
    }
    fun saveGender(gender: String) {
        val sharedPref = SaveSharedPrefHelper()
        val editor = sharedPref.saveStringItem("gender", gender, this)
    }

}