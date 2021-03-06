package com.example.dailydiet.bottomNavigationUI.dailydiet.Models

import com.google.gson.annotations.SerializedName

/* Food response class for  food search API
 */
class FoodsResponse {
    var totalHits: Int? = 0
    @SerializedName("foods")
    var foods = ArrayList<Foods>()
}

class Foods {
    @SerializedName("fdcId")
    var foodID:Int ?= 0
    var brandOwner:String ?= null
    var ingredients:String ?= null
    var description:String ?= null
}
