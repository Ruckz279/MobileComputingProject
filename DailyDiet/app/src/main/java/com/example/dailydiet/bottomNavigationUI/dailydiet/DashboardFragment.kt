package com.example.dailydiet.bottomNavigationUI.dailydiet

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailydiet.R
import com.example.dailydiet.SaveSharedPref
import com.example.dailydiet.bottomNavigationUI.dailydiet.Models.FoodItem
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_dashboard.*

import java.io.File
import java.io.FileWriter

class DashboardFragment : Fragment() {
      private lateinit var foodAdapter: FoodItemRecyclerAdapter
       var calorieBudget = 0
     // private lateinit var dashboardViewModel: DashboardViewModel
    private var file = "food_selection_list"
    var foodList = DataSource.createDataSet()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // dashboardViewModel = ViewModelProviders.of(this).get(initRecyclerView())

        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //get calorie budget
        val sharedPref = SaveSharedPref()
        val cal = sharedPref.getStringItem("expense", getContext()!!)
        if(cal != null){
            calorieBudget = cal!!.toInt()
        }
        initRecyclerView()
        addData()
    }

    fun updateItem(foodItem: FoodItem, mealType:String) {

        var item: FoodItem? = foodList.find { it.title == mealType }
        if(item != null) {
            foodItem.title = item!!.title + ": " + foodItem.title
            var temp = foodList.indexOf(item)
            foodList[temp] = foodItem
        }
        addData()
    }

    fun addFoodAction(item:FoodItem){
        val intent =  Intent(getActivity(), SearchActivity()::class.java)
        intent.putExtra("MENU_TITLE", item.title)
        getActivity()!!.startActivityForResult(intent,12345)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(data!=null){
            if(resultCode == -1){
                var foodItem = data.getSerializableExtra("MENU_ITEM") as FoodItem
                var title = data.getSerializableExtra("MENU_TITLE") as String
                updateItem(foodItem , title)
            }
        }
    }


    private fun addData(){
        //foodList = DataSource.createDataSet()
        updateCalorie()
        foodAdapter.submitList(foodList)

    }

    /*update calorie remainging for food selected

     */
    fun updateCalorie(){
        var consumedCalore = 0
        for (item in foodList){
            if(item.calorie!!.length >0) {
                var cal = item.calorie!!.toInt()
                consumedCalore = consumedCalore + cal
            }
        }
        var remainingBudget = calorieBudget - consumedCalore
        sumCalorie1.text = remainingBudget.toString() +" KCal"
    }



    private  fun initRecyclerView(){
        recycler.apply {
            layoutManager = LinearLayoutManager(context)
            foodAdapter = FoodItemRecyclerAdapter(this@DashboardFragment)
            adapter = foodAdapter
            foodAdapter.onItemClick = { fooditem ->

                // do something with your item
                Log.d("TAG", fooditem.calorie)
            }
        }
    }
}


