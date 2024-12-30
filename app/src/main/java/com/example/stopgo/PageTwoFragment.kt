package com.example.stopgo

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import androidx.fragment.app.Fragment

class PageTwoFragment : Fragment() {

    private val handler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.page_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startVibrationAnimation()

        handler.postDelayed(object : Runnable {
            override fun run() {
                startVibrationAnimation()
                handler.postDelayed(this, 10000)
            }
        }, 1000)
    }

    private fun startVibrationAnimation() {
        val imageViewFeuuuu: View = requireView().findViewById(R.id.imageViewFeuuuu)
        val right_arrow_button :View = requireView().findViewById(R.id.right_arrow_button)
        val vibration = TranslateAnimation(
            TranslateAnimation.ABSOLUTE, 0f,
            TranslateAnimation.ABSOLUTE, 20f,
            TranslateAnimation.ABSOLUTE, 0f,
            TranslateAnimation.ABSOLUTE, 0f
        )
        vibration.duration = 100
        vibration.repeatCount = 5
        vibration.repeatMode = TranslateAnimation.REVERSE

        imageViewFeuuuu.startAnimation(vibration)
        right_arrow_button.startAnimation(vibration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }
}
