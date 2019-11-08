package com.example.dailydiet.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dailydiet.R
import com.example.dailydiet.ui.dashboard.Models.FoodItem
import kotlinx.android.synthetic.main.fooditem_layout.view.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*

class FoodItemRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private  var items: List<FoodItem> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
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
                holder.bind(items.get(position))
            }
        }
    }

    fun submitList(foodList : List<FoodItem>){
        items = foodList
    }

    class FoodItemViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){
        val foodImage : ImageView = itemView.food_image
        val foodTitle : TextView = itemView.food_title
        val foodIngredients : TextView = itemView.food_author

        fun bind(foodItem: FoodItem){
            foodTitle.setText(foodItem.title)
            foodIngredients.setText(foodItem.ingredients)
            foodIngredients.setText(foodItem.calorie)
            //val requestOptions =RequestOptions().placeholder().error()
            Glide.with(itemView.context).load(foodItem.image).into(foodImage)

        }
    }


}