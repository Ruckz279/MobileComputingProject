package com.example.dailydiet.bottomNavigationUI.dailydiet

import com.example.dailydiet.bottomNavigationUI.dailydiet.Models.FoodItem

class DataSource {

    companion object{
       fun createDataSet(): ArrayList<FoodItem>{
            val list = ArrayList<FoodItem>()
            list.add(
                FoodItem(
                    null,
                    "BREAKFAST",
                    "CALORIE :",
                    "",
                    "",
                    "MENU"
                )
            )
            list.add(
               FoodItem(
                   null,
                   "SNACK",
                   "",
                   "",
                   "",
                   "MENU"
               )
            )
            list.add(
                FoodItem(

                    null,
                    "LUNCH",
                    "",
                    "",
                    "",
                    "MENU"
                )
            )

            list.add(
                FoodItem(
                    null,
                    "SNACK",
                    "",
                    "",
                    "",
                    "MENU"
                )
            )
            list.add(
                FoodItem(
                    null,
                    "DINNER",
                    "",
                    "",
                    "",
                "MENU"
                )
            )

            return list
        }
    }
}