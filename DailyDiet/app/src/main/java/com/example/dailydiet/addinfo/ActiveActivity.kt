package com.example.dailydiet.addinfo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.dailydiet.MainActivity
import com.example.dailydiet.R
import com.example.dailydiet.SaveSharedPref
import kotlinx.android.synthetic.main.activity_age.*

class ActiveActivity : AppCompatActivity() {
    var activity: String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_active)

    }

    fun clickListener(clicked: View) {
        val clickedButton = clicked as Button
        val tag = clickedButton.tag.toString()
        activity = tag.toString()
        saveActiveStatus(activity)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    fun saveActiveStatus(active: String) {
        val sharedPref = SaveSharedPref()
        val editor = sharedPref.saveStringItem("active", active, this)
    }
}
