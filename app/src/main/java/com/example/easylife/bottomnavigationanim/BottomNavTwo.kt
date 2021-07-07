package com.example.easylife.bottomnavigationanim

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.easylife.R
import com.ramotion.paperonboarding.PaperOnboardingFragment
import com.ramotion.paperonboarding.PaperOnboardingPage

class BottomNavTwo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_nav2)

//        fragmentManager = supportFragmentManager
//        val onBoardingFragment = PaperOnboardingFragment.newInstance(getDataForOnboarding())
//        val fragmentTransaction: android.app.FragmentTransaction? = fragmentManager!!.beginTransaction()
//        fragmentTransaction.add(R.id.fragment_container, onBoardingFragment)
//        fragmentTransaction.commit()
//
//        onBoardingFragment.setOnRightOutListener {
//            val fragmentTransaction: android.app.FragmentTransaction? = fragmentManager!!.beginTransaction()
//            val bf: Fragment = FragmentOne()
//            fragmentTransaction.replace(R.id.fragment_container, bf)
//            fragmentTransaction.commit()
//        }
    }

//    private fun getDataForOnboarding(): ArrayList<PaperOnboardingPage>? { // prepare data
//        val scr1 = PaperOnboardingPage("Hotels", "All hotels and hostels are sorted by hospitality rating", Color.parseColor("#678FB4"), R.drawable.hotels, R.drawable.key)
//        val scr2 = PaperOnboardingPage("Banks", "We carefully verify all banks before add them into the app", Color.parseColor("#65B0B4"), R.drawable.banks, R.drawable.wallet)
//        val scr3 = PaperOnboardingPage("Stores", "All local stores are categorized for your convenience", Color.parseColor("#9B90BC"), R.drawable.stores, R.drawable.shopping_cart)
//        val elements: ArrayList<PaperOnboardingPage> = ArrayList()
//        elements.add(scr1)
//        elements.add(scr2)
//        elements.add(scr3)
//        return elements
//    }
}

private fun Any?.replace(fragmentContainer: Int, bf: Fragment) {

}
