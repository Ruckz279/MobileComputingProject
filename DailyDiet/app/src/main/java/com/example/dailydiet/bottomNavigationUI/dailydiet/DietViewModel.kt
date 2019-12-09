package com.example.dailydiet.bottomNavigationUI.dailydiet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DietViewModel : ViewModel() {
    private lateinit var foodAdapter: FoodItemRecyclerAdapter

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}
