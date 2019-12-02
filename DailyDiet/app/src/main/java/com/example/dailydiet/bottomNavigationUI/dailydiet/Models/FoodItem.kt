package com.example.dailydiet.bottomNavigationUI.dailydiet.Models

import java.io.Serializable

data class FoodItem(
    var food_ID:Int?,
    var menu:String?,
    var title:String?,
    var calorie: String?,
    var brand:String?,
    var servingSize:String?,
    var servingUnit:String?,
    var ingredients:String?,
    var itemType:String?
):Serializable
