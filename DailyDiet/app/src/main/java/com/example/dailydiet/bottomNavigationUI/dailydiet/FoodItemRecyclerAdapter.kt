package com.example.dailydiet.bottomNavigationUI.dailydiet

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.dailydiet.R
import com.example.dailydiet.bottomNavigationUI.dailydiet.Models.FoodItem
import kotlinx.android.synthetic.main.fooditem_layout.view.*

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.example.dailydiet.SaveSharedPref


class FoodItemRecyclerAdapter(frag:Fragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    var items: List<FoodItem> = ArrayList()
    var onItemClick: ((FoodItem) -> Unit)? = null
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
                    //check if its search activity or the Menu activity(foodTitle tag as -1)
                    var context = holder.itemView.getContext()
                    if(holder.itemViewType== TYPE_MENU) {
                        (holder.itemView.food_ingredients.setTextColor(Color.GREEN))
                        val intent = Intent(mContext, SearchActivity::class.java)
                        intent.putExtra("MENU_TITLE", items.get(position).title)
                        frag.startActivityForResult(intent,Activity.RESULT_OK)

                    }
                    else {
                        //dismiss the search View
                        var f = items.get(position)
                        //pass the food item object
                        val returnIntent = Intent()
                        returnIntent.putExtra("MENU_TITLE",(context as SearchActivity).menu)
                        returnIntent.putExtra("MENU_ITEM",f)

                        (context as SearchActivity).setResult(Activity.RESULT_OK,returnIntent)
                        (context as SearchActivity).finish()
                        saveItem( (context as SearchActivity).menu,f, context)
                    }
                }
                holder.bind(items.get(position))
            }
        }
    }
    fun saveItem( menu:String,food:FoodItem, context: Context){
        val sharedPref= SaveSharedPref()
        var editor = sharedPref.saveStringItem(menu+"item",food.title.toString(),context)
        editor = sharedPref.saveStringItem(menu+"calorie",food.calorie.toString(),context)
    }
    override fun getItemViewType(position: Int): Int {
        val type = when (items[position].itemType) {
            "MENU"-> TYPE_MENU
            else -> TYPE_SEARCH
        }
        return type
    }
    fun submitList(foodList : List<FoodItem>){
        items = foodList

        notifyDataSetChanged()

    }

    class FoodItemViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){
        val foodImage : ImageView = itemView.food_image
        val foodTitle : TextView = itemView.food_title
        val foodIngredients : TextView = itemView.food_ingredients
        val foodCalorie: TextView = itemView.food_calorie
        val addFoodBtn : Button = itemView.add_Item

        fun bind(foodItem: FoodItem){
            foodTitle.setText(foodItem.title)
            foodIngredients.setText(foodItem.ingredients)
            foodCalorie.setText("CALORIE :" + foodItem.calorie.toString())
            //val requestOptions =RequestOptions().placeholder().error()
            //Glide.with(itemView.context).load(foodItem.image).into(foodImage)

        }


    }


}