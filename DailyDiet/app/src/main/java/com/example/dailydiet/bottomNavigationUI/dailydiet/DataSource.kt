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
                    "",
                    "",
                    "",
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
                    "",
                    "",
                    "",
                    "MENU"
                )
            )

            list.add(
                FoodItem(
                    null,
                    "SNACK-2",
                    "",
                    "",
                    "",
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
