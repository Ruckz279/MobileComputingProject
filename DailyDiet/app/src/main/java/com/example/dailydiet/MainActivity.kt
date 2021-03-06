package com.example.dailydiet

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dailydiet.addinfo.AgeActivity
import kotlin.math.round

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //set up bottom navigation view
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)
        // Passing each tab menu id as a set of Ids with home tab as top level destination
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        //get the saved user information from shared preference
        var (age, height, weight, gender, active) = getSavedValues()
        //if no infromation saved call intent to input user information
        if (active == null) {
            val intent = Intent(applicationContext, AgeActivity::class.java)
            startActivity(intent)
        }
        else {
            performCalculations(age!!, height!!, weight!!, gender!!, active!!)
        }
    }

    /* Get locally saved user information
        @Return Array : array of strings
     */
    private fun getSavedValues():Array<String?> {
        val sharedPref = SaveSharedPrefHelper()
        val age = sharedPref.getStringItem("age", this)
        val height = sharedPref.getStringItem("height", this)
        val weight = sharedPref.getStringItem("weight", this)
        val gender = sharedPref.getStringItem("gender", this)
        val active = sharedPref.getStringItem("active", this)
        return arrayOf(age,height,weight,gender,active)
    }

    /*Perform calculations BMI , category, evaluate BMR  and the calorie expense
     *save evaluations locally
     */
    private fun performCalculations(age:String,height: String,weight: String,gender: String,active: String){
        val BMI = evaluateBMI(age, height, weight)
        val category = getCategory(BMI)
        val BMR = evaluateBMR(age,height, weight,gender)
        val calorieExpense = evaluateCalorie(BMR,active)

        //save the estimated user data locally
        val sharedPref= SaveSharedPrefHelper()
        sharedPref.saveStringItem("expense",calorieExpense.toString(),this)
        sharedPref.saveStringItem("category",category,this)
        sharedPref.saveStringItem("BMI",BMI.toString(),this)
    }

    /* Evaluate BMI
       @Return BMI value as Double
     */
    private fun evaluateBMI(age:String, height:String, weight:String): Double {
        var heightInMeters = height.toDouble()/100
        val temp = Math.pow(heightInMeters,2.0)
        val BMI = weight.toDouble()/temp
        return round(BMI*100)/100.toDouble()
    }

    /* Evaluate category
       @Return category value as String
     */
    private fun getCategory(BMI :Double): String {
        var category:String = "NONE"
        when {
            BMI < 18.5 -> category = "UNDERWEIGHT"
            BMI >= 18.5 && BMI <= 24.9 -> category = "NORMAL"
            BMI >= 25 && BMI <= 29.9 -> category = "OVERWEIGHT"
            BMI >= 30 -> category = "OBESE"
        }
        return category
    }

    /* Evaluate BMR
       @Return BMR value as Double
     */
    private fun evaluateBMR(age:String, height:String, weight:String, gender:String): Double {
        var BMR:Double = 0.0
        if(gender == "MALE") {
            BMR = (10 * weight.toDouble()) + (6.25 * height.toDouble()) - (5 * age.toInt()) + 5
        }
        else {
            BMR = (10 * weight.toDouble()) + (6.25 * height.toDouble()) - (5 * age.toInt()) + 5 - 161
        }
      return BMR
    }

    /* Evaluate calorie expense
      @Return calorie value as Double
    */
    private fun evaluateCalorie(BMR: Double, active:String): Int{
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

    //Redirect on activity result to fragment manager
    override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fm = (this).getSupportFragmentManager()
        val fragment = fm.findFragmentById(R.id.nav_host_fragment)
        val child = fragment?.childFragmentManager
        child?.primaryNavigationFragment?.onActivityResult(requestCode, resultCode, data)
    }

    //Setting Up the back button
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    override fun onBackPressed() {
        moveTaskToBack(false)
    }
}
