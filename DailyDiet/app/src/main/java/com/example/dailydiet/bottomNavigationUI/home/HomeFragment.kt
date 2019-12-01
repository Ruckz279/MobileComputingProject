package com.example.dailydiet.bottomNavigationUI.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.dailydiet.R
import com.example.dailydiet.SaveSharedPrefHelper



class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val weight:Button = root.findViewById(R.id.weight)
        val budget:Button = root.findViewById(R.id.budget)
        val status:Button = root.findViewById(R.id.status)
        val bmi:Button = root.findViewById(R.id.bmi)
        //homeViewModel.weight.observe(this, Observer {
        val sharedPref = SaveSharedPrefHelper()
        val calorie = sharedPref.getStringItem("expense", getContext()!!)
        val weght = sharedPref.getStringItem("weight",getContext()!!)
        val categry = sharedPref.getStringItem("category",getContext()!!)
        val str = calorie +" KCal"
        val bmiVal = sharedPref.getStringItem("BMI",getContext()!!)
        weight.text = weght + " KG"
        budget.text = str
        status.text = categry
        bmi.text = bmiVal

        //})

        return root
    }
}