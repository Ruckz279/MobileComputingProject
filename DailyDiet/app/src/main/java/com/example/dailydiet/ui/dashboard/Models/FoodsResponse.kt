package com.example.dailydiet.ui.dashboard.Models

import com.google.gson.annotations.SerializedName

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
