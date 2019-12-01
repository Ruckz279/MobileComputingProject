package com.example.dailydiet.bottomNavigationUI.dailydiet

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
import com.example.dailydiet.SaveSharedPrefHelper
import com.example.dailydiet.bottomNavigationUI.dailydiet.Models.FoodItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {
    private lateinit var foodAdapter: FoodItemRecyclerAdapter
    var calorieBudget = 0
    val sharedPref = SaveSharedPrefHelper()
     // private lateinit var dashboardViewModel: DashboardViewModel
    private var file = "food_selection_list"
    lateinit var foodList :ArrayList<FoodItem>

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
        val sharedPref = SaveSharedPrefHelper()
        val cal = sharedPref.getStringItem("expense", getContext()!!)
        if(cal != null){
            calorieBudget = cal.toInt()
        }
        foodList = getFoodChoice()
        initRecyclerView()
        addData()
    }

    fun updateItem(foodItem: FoodItem, mealType:String) {
        var item: FoodItem? = foodList.find { it.menu == mealType }
        if(item != null) {
            var temp = foodList.indexOf(item)
            foodItem.itemType = "MENU"
            foodList[temp] = foodItem
        }
        saveFoodChoices(foodList)
        addData()
    }

    fun addFoodAction(item:FoodItem){
        val intent =  Intent(getActivity(), SearchActivity()::class.java)
        intent.putExtra("MENU_TITLE", item.menu)
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
        updateCalorie()
        foodAdapter.submitList(foodList)
    }

    /* save food choice as json string

     */
    fun saveFoodChoices(DataArrayList: ArrayList<FoodItem>) {
        val jsonString = Gson().toJson(DataArrayList)
        sharedPref.saveStringItem("FoodChoice",jsonString, context!!)
    }

    /*get saved food choices as array list

     */
    fun getFoodChoice():ArrayList<FoodItem>{
        var foodCoice = sharedPref.getStringItem("FoodChoice",context!!)
        if(foodCoice != null) {
            val collectionType = object : TypeToken<ArrayList<FoodItem>>() {}.type
            var flist = Gson().fromJson(foodCoice, collectionType) as ArrayList<FoodItem>
            return flist
        }
        else{
            return DataSource.createDataSet()
        }
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
        }
    }
}


