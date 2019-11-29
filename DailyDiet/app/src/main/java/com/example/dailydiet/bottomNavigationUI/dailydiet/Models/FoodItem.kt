package com.example.dailydiet.bottomNavigationUI.dailydiet.Models

import java.io.Serializable

data class FoodItem(
    var food_ID:Int?,
    var title:String?,
    var calorie: String?,
    var brand:String?,
    var servingSize:String?,
    var servingUnit:String?,
    var ingredients:String?,
    var itemType:String?
):Serializable

private var jsonTags = listOf("food_id","title","calorie","brand","ingredients","itemType")
