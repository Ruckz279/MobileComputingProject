package com.example.dailydiet.bottomNavigationUI.dailydiet

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailydiet.R
import com.example.dailydiet.bottomNavigationUI.dailydiet.Models.*
import kotlinx.android.synthetic.main.activity_search.*

import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.collections.ArrayList


import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast


class SearchActivity(): AppCompatActivity() {

    private lateinit var foodAdapter: FoodItemRecyclerAdapter
    lateinit var mealType: String
    // prepare to make call in Retrofit 2.0 to parse the API as baseurl
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    //retrofit service
    val service = retrofit.create(FoodService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        var searchItem: String
        //identify meal type for search
        mealType = intent.getStringExtra("MENU_TITLE")
        initRecyclerView()
        search.setOnClickListener {
            searchItem = search.text.toString()
            showKeyboard()
            if (searchItem.length != 0) {
                searchFood(searchItem)
                search.text = null
            }
        }
    }

    //base url and api key as companion object
    companion object {
        var baseUrl = "https://api.nal.usda.gov/"
        var api_key = "DplHTnW193Hm4kgiAGHKPUTAginfkQsRbClBEI5X"
    }

    /* Initialise recycler view to list search items
     */
    private fun initRecyclerView() {
        recycler_search.apply {
            layoutManager = LinearLayoutManager(context)
            foodAdapter = FoodItemRecyclerAdapter(DietFragment())
            adapter = foodAdapter
        }
    }

    /*search for food in the database
      searchItem :  search item as string
     */
    fun searchFood(searchItem: String) {
        var paramObject = JSONObject()
        paramObject.put("generalSearchInput", searchItem);
        paramObject.put("api_key", api_key);

        var json = paramObject.toString()
        var body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
        val callSearch = service.searchFood(body, api_key)

        hideKeyboard()
        progressBar.visibility = View.VISIBLE
        //handle call back response
        callSearch.enqueue(object : Callback<FoodsResponse> {
            override fun onResponse(call: Call<FoodsResponse>, response: Response<FoodsResponse>) {
                progressBar.visibility = View.GONE
                if (response.code() == 200) {
                    val foodsResponse = response.body()!!
                    //create food object for recycler view
                    recyclerFoodObject(foodsResponse)
                } else {
                    progressBar.visibility = View.GONE
                    Toast.makeText(
                        applicationContext,
                        "Sorry .. Unable to connect",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            override fun onFailure(call: Call<FoodsResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(applicationContext, "Sorry .. Unable to connect", Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    /* create food object for recycler view
       dataArray : response from retrofit service as FoodResponse object
    */
    fun recyclerFoodObject(dataArray: FoodsResponse) {
        var foodArrayList: MutableList<FoodItem> = ArrayList()
        //create the Fooditem objects
        for (item in dataArray.foods) {
            var foodItem1: Foods = item
            var foodItem: FoodItem = FoodItem(null, null, null, null, null, null, null, null, null)
            foodItem.title = foodItem1.description
            foodItem.ingredients = item.ingredients
            foodItem.food_ID = item.foodID
            foodItem.brand = item.brandOwner
            foodItem.itemType = "SEARCH"
            // In call back update the recycler view with ArrayList of Fooditem objects
            getCalorie(foodItem) { foodItem ->
                foodArrayList.add(foodItem)
                if (foodArrayList.size == (dataArray.foods).size)
                    progressBar.visibility = View.GONE
                foodAdapter.submitList(foodArrayList)
            }
        }

    }

    /* Get calorie, serving size and serving unit of a food item .Use retrofit API to perfom GET for a food item
       foodItem : food item as FoodItem object
       callback : to return and update the list with calorie
     */
    fun getCalorie(foodItem: FoodItem, callback: (FoodItem) -> Unit) {
        //get calorie of a selected food id
        var calorie = 0.00
        val call = service.getFoodDetails(foodItem.food_ID, api_key)
        call.enqueue(object : Callback<FoodDetailResponse> {
            override fun onResponse( call: Call<FoodDetailResponse>, response: Response<FoodDetailResponse>) {
                progressBar.visibility = View.GONE
                if (response.code() == 200) {
                    val foodDetailResponse = response.body()!!
                    var serving = foodDetailResponse.servingSize
                    //Calorie per 100g if serving information is null
                    if (serving!= null) {
                        foodItem.servingSize = serving.toString()
                        foodItem.servingUnit = foodDetailResponse.unit
                    }
                    else {
                        serving = 100.00
                    }
                    for (item in foodDetailResponse.nutrients) {
                        if (item.nutrients.id == 1008 && item.amount != null) {
                            calorie = item.amount!!.toDouble()
                            //Evaluate calorie per serving
                            var calPergram = (calorie/100.00)
                            calorie = calPergram *serving
                            foodItem.calorie = calorie.toInt().toString()
                            callback(foodItem)
                            break
                        }
                    }
                }
                else {
                    Toast.makeText(
                        applicationContext,
                        "Sorry .. Unable to connect",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            override fun onFailure(call: Call<FoodDetailResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(applicationContext, "Sorry .. Unable to connect", Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    /* Dismiss the search activity with set result in return intent
      fooditem : selected food item as FoodItem object
    */
    fun dismissSearch(fooditem: FoodItem) {
        val returnIntent = Intent()
        returnIntent.putExtra("MENU_TITLE", mealType)
        fooditem.menu = mealType
        returnIntent.putExtra("MENU_ITEM", fooditem)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }
}

/* To hide keyboard
 */
fun AppCompatActivity.hideKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
}

/* To show keyboard
 */
fun AppCompatActivity.showKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, 0)
    }
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
}

