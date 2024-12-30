package com.example.stopgo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter


        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0) {
                    val imageView = findViewById<ImageView>(R.id.cercle)
                    val moveUpDownAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.animation)
                    imageView.startAnimation(moveUpDownAnimation)
                }
            }
        })






    }
}
