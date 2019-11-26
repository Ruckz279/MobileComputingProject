package com.example.dailydiet.bottomNavigationUI.dailydiet

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailydiet.R
import com.example.dailydiet.SaveSharedPref
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {
      private lateinit var foodAdapter: FoodItemRecyclerAdapter
      private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // dashboardViewModel = ViewModelProviders.of(this).get(initRecyclerView())

        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = SaveSharedPref()
        val calorie = sharedPref.getStringItem("expense", getContext()!!)
        sumCalorie1.text = calorie +" KCal"
        initRecyclerView()
        addData()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //super.onActivityResult(requestCode, resultCode, data)
        if(data!=null){
            if(resultCode == Activity.RESULT_OK){
                var foodItem = data.getSerializableExtra("MENU_ITEM")
            }

        }
    }


    private fun addData(){
        val data = DataSource.createDataSet()
        foodAdapter.submitList(data)
    }

    private  fun initRecyclerView(){
        recycler.apply {
            layoutManager = LinearLayoutManager(context)
            foodAdapter = FoodItemRecyclerAdapter(this@DashboardFragment)
            adapter = foodAdapter
            foodAdapter.onItemClick = { fooditem ->

                // do something with your item
                Log.d("TAG", fooditem.calorie)
            }
        }
    }
}


