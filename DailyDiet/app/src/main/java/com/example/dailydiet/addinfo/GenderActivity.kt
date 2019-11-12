package com.example.dailydiet.addinfo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ToggleButton
import com.example.dailydiet.R
import com.example.dailydiet.SaveSharedPref
import kotlinx.android.synthetic.main.activity_gender.*

class GenderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        var gender:String = ""
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gender)
        next4.setOnClickListener() {
            val gender = "MALE"
            saveGender(gender)
            val intent = Intent(this, ActiveActivity::class.java)
            startActivity(intent)
        }


        maleToggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // The toggle is enabled
                gender = "1"
            } else {
                femaleToggle.isChecked
                gender = "2"// The toggle is disabled
            }
        }
        // Set an checked change listener for toggle button
        femaleToggle.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                gender ="2"
                // The toggle is enabled/checked
                //Toast.makeText(applicationContext, "Toggle on", Toast.LENGTH_SHORT).show()

                // Change the app background color
                //root_layout.setBackgroundColor(Color.GREEN)
            } else {
                maleToggle.isChecked
                gender ="1"
                // The toggle is disabled
                //Toast.makeText(applicationContext, "Toggle off", Toast.LENGTH_SHORT).show()

                // Set the app background color to gray
                //root_layout.setBackgroundColor(Color.GRAY)
            }
        }
    }
    fun saveGender(gender: String) {
        val sharedPref = SaveSharedPref()
        val editor = sharedPref.saveStringItem("gender", gender, this)
    }

}