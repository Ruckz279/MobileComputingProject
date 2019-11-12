package com.example.dailydiet

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dailydiet.addinfo.AgeActivity
import kotlinx.android.synthetic.main.activity_age.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.nio.file.Files.size
import com.example.dailydiet.SaveSharedPref as SaveSharedPref

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        var (age, height, weight, gender,active) = getSavedValues()
        if (gender == null) {
            val intent = Intent(applicationContext, AgeActivity::class.java)
            startActivity(intent)
        }
        else {
           val BMI = evaluateBMI(age.toString(), height.toString(), weight.toString())
           val category = getCategory(BMI)
           val BMR = evaluateBMR(age.toString(),height.toString(), weight.toString(),gender.toString())
            val calorieExpense = evaluateCalorie(BMR,active.toString())
        }

    }
    fun getSavedValues():Array<String?> {
        val sharedPref = SaveSharedPref()
        val age = sharedPref.getStringItem("age", this)
        val height = sharedPref.getStringItem("height", this)
        val weight = sharedPref.getStringItem("weight", this)
        val gender = sharedPref.getStringItem("gender", this)
        val active = sharedPref.getStringItem("active", this)
        return arrayOf(age,height,weight,gender,active)
    }

    fun evaluateBMI(age:String, height:String, weight:String): Double {
        var heightInMeters = height.toDouble()/100
        val temp = Math.pow(heightInMeters.toDouble(),2.0)
        val BMI = weight.toDouble()/temp
        return BMI
    }

    fun getCategory(BMI :Double): String {
        var category:String = "NONE"

        when {
            BMI < 18.5 -> category = "UNDERWEIGHT"
            BMI >= 18.5 && BMI <= 24.9 -> category = "NORMAL"
            BMI >= 25 && BMI <= 29.9 -> category = "OVERWEIGHT"
            BMI >= 30 -> category = "OBESE"
        }
        return category
    }

    fun evaluateBMR(age:String, height:String, weight:String, gender:String): Double {
        var BMR:Double = 0.0
        val heightInMeter = height.toDouble()/100
        if(gender == "MALE") {
            BMR = (10 * weight.toDouble()) + (6.25 * heightInMeter) - (5 * age.toInt()) + 5
        }
        else {
            BMR = (10 * weight.toDouble()) + (6.25 * heightInMeter) - (5 * age.toInt()) + 5 - 161
        }
      return BMR
    }
    fun evaluateCalorie(BMR: Double, active:String): Double{
        return 5657.0
    }
}
