package com.example.stopgo

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(activity: MainActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PageOneFragment()
            1 -> PageTwoFragment()
            2 -> TrafficLightFragment()
            else -> PageOneFragment()
        }
    }
}
