package com.example.dailydiet.bottomNavigationUI.dailydiet.Models

import com.google.gson.annotations.SerializedName

class FoodDetailResponse {

    @SerializedName("description")
    var description: String? = null
    @SerializedName("foodNutrients")
    var nutrients = ArrayList<FoodNutrients>()
    //@SerializedName("inputFoods")
    //var inputFoods=ArrayList<InputFoods>()
   // var inputFoods=InputFoods()
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

class InputFoods{
    var portionDescription : String?=null
    var portionCode : String?=null
    var gramWeight:Int?=0
}