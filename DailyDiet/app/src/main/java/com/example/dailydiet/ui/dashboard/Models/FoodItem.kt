package com.example.dailydiet.ui.dashboard.Models

import java.io.Serializable

data class FoodItem(
    var food_ID:Int?,
    var title:String?,
    var calorie: String?,
    var image:String?,
    var ingredients:String?,
    var itemType:String?
):Serializable
