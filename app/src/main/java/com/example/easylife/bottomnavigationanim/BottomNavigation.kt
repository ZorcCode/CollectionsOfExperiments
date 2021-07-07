package com.example.easylife.bottomnavigationanim

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.easylife.R
import com.example.easylife.kotlinfiles.MovieModel
import com.example.easylife.kotlinfiles.RecyclerViewAdapter
import eu.long1.spacetablayout.SpaceTabLayout

class BottomNavigation : AppCompatActivity() {
    var tabLayout: SpaceTabLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)
        val fragmentList: MutableList<Fragment> = ArrayList()
        fragmentList.add(FragmentOne())
        fragmentList.add(FragmentTwo())
        fragmentList.add(FragmentThree())
        fragmentList.add(FragmentTwo())
        fragmentList.add(FragmentThree())
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        tabLayout = findViewById(R.id.spaceTabLayout)
        tabLayout!!.initialize(viewPager, supportFragmentManager, fragmentList, null)


    }


}