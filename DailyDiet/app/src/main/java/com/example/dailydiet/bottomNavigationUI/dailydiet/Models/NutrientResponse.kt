package com.example.dailydiet.bottomNavigationUI.dailydiet.Models

import com.google.gson.annotations.SerializedName

class FoodDetailResponse {

    @SerializedName("description")
    var description: String? = null
    @SerializedName("servingSizeUnit")
    var unit: String? = null
    @SerializedName("servingSize")
    var servingSize: Double? = null
    @SerializedName("foodNutrients")
    var nutrients = ArrayList<FoodNutrients>()

   // var  inputFoods = InputFoods()
}
   // var inputFoods=InputFoods()

class FoodNutrients {
    var id: Int? = 0
    var amount:String ?= null
    @SerializedName("nutrient")
    var nutrients = Nutrient()
}
class Nutrient {
    var id: Int? = 0
    var name: String? = null
    var unitNme: String? = null
    var rank: Int? = 0
}

