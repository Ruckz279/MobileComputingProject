package com.example.dailydiet.ui.dashboard.Models

import com.google.gson.annotations.SerializedName

class FoodDetailResponse {

    @SerializedName("description")
    var description: String? = null
    @SerializedName("foodNutrients")
    var nutrients = ArrayList<FoodNutrients>()
}

class FoodNutrients {
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