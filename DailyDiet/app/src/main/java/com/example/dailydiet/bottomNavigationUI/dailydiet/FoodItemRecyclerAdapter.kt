package com.example.dailydiet.bottomNavigationUI.dailydiet

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dailydiet.R
import com.example.dailydiet.bottomNavigationUI.dailydiet.Models.FoodItem
import kotlinx.android.synthetic.main.fooditem_layout.view.*
import android.content.Context


/*Recycler adapter class for menu items and food search items

 */
class FoodItemRecyclerAdapter(frag:DietFragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    var items: List<FoodItem> = ArrayList()
    var frag = frag
    lateinit var mContext:Context
    //to identify which activity the recycler is used
    companion object {
        const val TYPE_MENU = 0
        const val TYPE_SEARCH = 1
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.getContext();
        return FoodItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fooditem_layout,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is FoodItemViewHolder ->{
                holder.addFoodBtn.setOnClickListener {
                    //check if its search activity or the Menu activity
                    var context = holder.itemView.getContext()
                    if(holder.itemViewType == TYPE_MENU) {
                        frag.addFoodAction(items.get(position))
                    }
                    else if(holder.itemViewType == TYPE_SEARCH) {
                        //dismiss the search View
                        var f = items.get(position)
                        (context as SearchActivity).dismissSearch(f)
                    }
                }
                holder.itemView.setOnClickListener{
                    //selected food detail ingredients
                    //if(holder.itemViewType != TYPE_MENU){
                        val intent = Intent(mContext, DetailIngredientsActivity::class.java)
                        intent.putExtra("FOOD_TITLE", items.get(position).title)
                        intent.putExtra("FOOD_INGR", items.get(position).ingredients)
                        mContext.startActivity(intent)
                    //}
                }
                holder.bind(items.get(position),holder.itemViewType)
            }
        }
    }

    /* Recycler view holder class
     */
    class FoodItemViewHolder constructor( itemView: View): RecyclerView.ViewHolder(itemView){
        //get the ui elements to bind data
        val foodTitle : TextView = itemView.food_title
        val foodIngredients : TextView = itemView.food_ingredients
        val foodCalorie: TextView = itemView.food_calorie
        val addFoodBtn : Button = itemView.add_Item
        //bind food item to recycler list view
        fun bind(foodItem: FoodItem, itemType:Int){
            if( itemType == TYPE_MENU){
                var temp = (foodItem.menu + " : " +foodItem.title)
                foodTitle.setText(temp)
            }
            else if(itemType == TYPE_SEARCH) {
                foodTitle.setText(foodItem.title)
            }
            //clear text views
            foodIngredients.setText("")
            foodCalorie.setText("")
            if(foodItem.brand != "null")
                foodIngredients.setText(foodItem.brand)
            if(foodItem.calorie.toString().length > 1){
                foodCalorie.setText(setCalorieText(foodItem))
            }
        }
        //set customized  calorie text
        fun setCalorieText(foodItem: FoodItem):String{
            var temp = foodItem.servingSize + foodItem.servingUnit
            if(temp!= "nullnull"){
                temp = foodItem.calorie.toString()+" CALORIE PER  "+ temp
            }
            else{
                temp = foodItem.calorie.toString()+" CALORIE PER 100gm"
            }
            return temp
        }

    }

    /*Get the item view type.
      Distinguish the view type , menu or search view
     */
    override fun getItemViewType(position: Int): Int {
        val type = when (items[position].itemType) {
            "MENU"-> TYPE_MENU
            "SEARCH" -> TYPE_SEARCH
             else -> -1
        }
        return type
    }

    /*submit list of objects to display in recycler view
      foodList : List of FoodItem objects to display in recycler
     */
    fun submitList(foodList : List<FoodItem>){
        items = foodList
        notifyDataSetChanged()
    }

}