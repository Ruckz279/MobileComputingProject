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
import com.example.dailydiet.SaveSharedPrefHelper


class SearchActivity(): AppCompatActivity() {

    private lateinit var foodAdapter: FoodItemRecyclerAdapter
    lateinit var menu:String
    // prepare call in Retrofit 2.0
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val service = retrofit.create(FoodService::class.java)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        var searchItem: String
        menu = intent.getStringExtra("MENU_TITLE")
        search.setOnClickListener {

            searchItem = search.text.toString()
            showKeyboard()
            if (searchItem.length !=0) {
                //search for food from API
                searchFood(searchItem)
                search.text = null
            }
        }
        initRecyclerView()

    }
    companion object {
        var baseUrl = "https://api.nal.usda.gov/"
        var api_key = "DplHTnW193Hm4kgiAGHKPUTAginfkQsRbClBEI5X"
    }

    private fun initRecyclerView() {
        recycler2.apply {
            layoutManager = LinearLayoutManager(context)
            //var fragment = supportFragmentManager.
            //Fragment uploadType = getChildFragmentManager().findFragmentById(R.id.container_framelayout);
            foodAdapter = FoodItemRecyclerAdapter(DashboardFragment())
            adapter = foodAdapter
        }
    }

    fun searchFood(searchItem: String) {
        var paramObject = JSONObject()
        var food_id = 0
        paramObject.put("generalSearchInput", searchItem);
        paramObject.put("api_key", api_key);

        var json = paramObject.toString()
        var body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        val callSearch = service.searchFood(body, api_key)
        hideKeyboard()
        progressBar.visibility = View.VISIBLE
        callSearch.enqueue(object : Callback<FoodsResponse> {
            override fun onResponse(
                call: Call<FoodsResponse>,
                response: Response<FoodsResponse>
            ) {
                if (response.code() == 200) {
                    val foodsResponse = response.body()!!
                    recyclerFoodObject(foodsResponse)
                }
                else{
                    progressBar.visibility = View.GONE
                    Toast.makeText(applicationContext, "Sorry .. Unable to connect", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<FoodsResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(applicationContext, "Sorry .. Unable to connect", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun dismissSearch(fooditem:FoodItem){
        val returnIntent = Intent()
        returnIntent.putExtra("MENU_TITLE",menu)
        fooditem.menu = menu
        returnIntent.putExtra("MENU_ITEM",fooditem)
        setResult(Activity.RESULT_OK,returnIntent)
        finish()
    }
    fun getCalorie(foodItem :FoodItem, callback:(FoodItem)->Unit) {
        //get calorie of a selected food id
        var calorie: Int = 0
        val call = service.getFoodDetails(foodItem.food_ID, api_key)
        call.enqueue(object : Callback<FoodDetailResponse> {
            override fun onResponse(
                call: Call<FoodDetailResponse>,
                response: Response<FoodDetailResponse>
            ){
                if (response.code() == 200) {
                    val foodDetailResponse = response.body()!!
                    foodItem.servingSize = foodDetailResponse.servingSize.toString()
                    foodItem.servingUnit = foodDetailResponse.unit
                    for (item in foodDetailResponse.nutrients) {
                        if (item.nutrients.id == 1008 && item.amount != null) {
                            calorie = (item.amount!!.toDouble()).toInt()
                            foodItem.calorie = calorie.toString()
                            progressBar.visibility = View.GONE
                            callback(foodItem)
                            break
                        }
                    }
                }
                else{
                    progressBar.visibility = View.GONE
                    Toast.makeText(applicationContext, "Sorry .. Unable to connect", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<FoodDetailResponse>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(applicationContext, "Sorry .. Unable to connect", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun recyclerFoodObject(dataArray: FoodsResponse) {
        var foodArrayList: MutableList<FoodItem> = ArrayList()
        for (item in dataArray.foods) {
            var foodItem1:Foods = item
            var foodItem: FoodItem = FoodItem(null,null,null,null,null, null,null,null,null)
            foodItem.title = foodItem1.description
            foodItem.ingredients = item.ingredients
            foodItem.food_ID = item.foodID
            foodItem.brand = item.brandOwner
            foodItem.itemType ="SEARCH"
            // In call back update the recycler view with foodArrayList
            getCalorie(foodItem){foodItem->
                foodArrayList.add(foodItem)
                if(foodArrayList.size == (dataArray.foods).size)
                    progressBar.visibility = View.GONE
                    foodAdapter.submitList(foodArrayList)
            }
        }

    }
}



fun AppCompatActivity.hideKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
    // else {
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    // }
}

fun AppCompatActivity.showKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, 0)
    }
    // else {
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    // }
}
