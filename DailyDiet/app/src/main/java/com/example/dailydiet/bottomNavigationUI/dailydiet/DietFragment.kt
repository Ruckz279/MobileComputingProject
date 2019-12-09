package com.example.dailydiet.bottomNavigationUI.dailydiet

import android.content.Intent
import android.os.Bundle
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

/* Diet fragment for Today's tab in bottom navigation
*/
class DietFragment : Fragment() {
    private lateinit var foodAdapter: FoodItemRecyclerAdapter
    var calorieBudget = 0
    val sharedPref = SaveSharedPrefHelper()
    private var file = "food_selection_list"
    lateinit var foodList :ArrayList<FoodItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        //get saved food choices if any initialize recycler and data to it
        foodList = getFoodChoice()
        initRecyclerView()
        addData()
    }

    /* get saved food choices as array list

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

    /* initialize recycler view with lay out manger and recycler adapter
    */
    private  fun initRecyclerView(){
        recycler.apply {
            layoutManager = LinearLayoutManager(context)
            foodAdapter = FoodItemRecyclerAdapter(this@DietFragment)
            adapter = foodAdapter
        }
    }

    /* submit the data to recycler adapter
     */
    private fun addData(){
        updateCalorie()
        foodAdapter.submitList(foodList)
    }

    /*update calorie remaining after food selection
    */
    fun updateCalorie(){
        var consumedCalore = 0
        // check calorie for each food item
        for (item in foodList){
            if(item.calorie!!.length >0) {
                var cal = item.calorie!!.toInt()
                consumedCalore = consumedCalore + cal
            }
        }
        //evaluate remainin calorie and update the activity text
        var remainingBudget = calorieBudget - consumedCalore
        sumCalorie1.text = remainingBudget.toString() +" KCal"

    }

    /* onActivity result handles the food selection data from return intent

     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(data!=null){
            if(resultCode == -1){
                var foodItem = data.getSerializableExtra("MENU_ITEM") as FoodItem
                var title = data.getSerializableExtra("MENU_TITLE") as String
                updateItem(foodItem , title)
            }
        }
    }

    /* update the respective meals with a food item selected
       foodItem : food item selected as FoodItem
       mealType : meal type as String
     */
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

    /* save food choice as json string
     */
    fun saveFoodChoices(DataArrayList: ArrayList<FoodItem>) {
        val jsonString = Gson().toJson(DataArrayList)
        sharedPref.saveStringItem("FoodChoice",jsonString, context!!)
    }

    /* action for add button in each menu item
        provide intent with menu title information
     */
    fun addFoodAction(item:FoodItem){
        val intent =  Intent(getActivity(), SearchActivity()::class.java)
        intent.putExtra("MENU_TITLE", item.menu)
        getActivity()!!.startActivityForResult(intent,12345)
    }
}


