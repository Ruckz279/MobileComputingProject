package com.example.dailydiet

import android.app.Activity
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
import kotlin.math.round
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
        if (active == null) {
            val intent = Intent(applicationContext, AgeActivity::class.java)
            startActivity(intent)
        }
        else {
           val BMI = evaluateBMI(age.toString(), height.toString(), weight.toString())
           val category = getCategory(BMI)
           val BMR = evaluateBMR(age.toString(),height.toString(), weight.toString(),gender.toString())
            val calorieExpense = evaluateCalorie(BMR,active.toString())
            //sumCalorie.text = "CALORIE BUDGET : "+calorieExpense + "KCal"

            val sharedPref= SaveSharedPref()
            sharedPref.saveStringItem("expense",calorieExpense.toString(),this)
            sharedPref.saveStringItem("category",category,this)
            sharedPref.saveStringItem("BMI",BMI.toString(),this)
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
        val temp = Math.pow(heightInMeters,2.0)
        val BMI = weight.toDouble()/temp
        return round(BMI*100)/100.toDouble()

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

        if(gender == "MALE") {
            BMR = (10 * weight.toDouble()) + (6.25 * height.toDouble()) - (5 * age.toInt()) + 5
        }
        else {
            BMR = (10 * weight.toDouble()) + (6.25 * height.toDouble()) - (5 * age.toInt()) + 5 - 161
        }
      return BMR
    }
    fun evaluateCalorie(BMR: Double, active:String): Int{
        var activeStatus =  active.toDouble()
        when (activeStatus){
            1.00 -> activeStatus = 1.2
            2.00 -> activeStatus = 1.375
            3.00 -> activeStatus = 1.55
            4.00 -> activeStatus = 1.725
            5.00 -> activeStatus = 1.9
        }
        var calorieExpense = BMR * activeStatus
        return calorieExpense.toInt()
    }
    override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data!=null){
            if(resultCode == Activity.RESULT_OK){
                var foodItem = data.getSerializableExtra("MENU_ITEM")
            }

        }
    }
}
