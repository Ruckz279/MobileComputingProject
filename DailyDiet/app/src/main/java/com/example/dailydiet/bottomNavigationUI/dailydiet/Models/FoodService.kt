package com.example.dailydiet.bottomNavigationUI.dailydiet.Models

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface FoodService {
    //Food search endpoint
    @POST("fdc/v1/search?")
    fun searchFood(
        @Body requestBody :RequestBody,
        @Query("api_key") api_key: String
    ):Call<FoodsResponse>

    //Food Details endpoint
    @GET("fdc/v1/{Id}?")
    fun getFoodDetails(@Path("Id") id: Int?,
                       @Query("api_key") api_key: String
    ):Call<FoodDetailResponse>
}