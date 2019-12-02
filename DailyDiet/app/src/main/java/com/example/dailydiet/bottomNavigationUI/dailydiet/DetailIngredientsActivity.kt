package com.example.dailydiet.bottomNavigationUI.dailydiet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dailydiet.R
import kotlinx.android.synthetic.main.activity_detail_ingredients.*

class DetailIngredientsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_ingredients)
        var title = intent.getStringExtra("FOOD_TITLE")

        var indgred = intent.getStringExtra("FOOD_INGR")
        tiltle.text = title 
        ingredients.text = "INGREDIENTS:  " + indgred
    }
}
