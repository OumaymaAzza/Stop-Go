package com.example.stopgo

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import java.util.*

class TrafficLightFragment : Fragment() {

    private lateinit var redLight: View
    private lateinit var yellowLight: View
    private lateinit var greenLight: View
    private lateinit var stopImage: ImageView
    private lateinit var warningImage: ImageView
    private lateinit var goImage: ImageView
    private val handler = Handler()

    private var isYellowActivated = false
    private var isTrafficCycleStarted = false

    private var redLightDuration = 2000L
    private var yellowLightDuration = 5000L
    private var greenLightDuration = 8000L

    private lateinit var poppinsRegular: Typeface
    private lateinit var poppinsBold: Typeface

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.page_three, container, false)

        poppinsRegular = ResourcesCompat.getFont(requireContext(), R.font.poppins)!!
        poppinsBold = ResourcesCompat.getFont(requireContext(), R.font.poppinsbold)!!

        redLight = view.findViewById(R.id.redLight)
        yellowLight = view.findViewById(R.id.yellowLight)
        greenLight = view.findViewById(R.id.greenLight)

        stopImage = view.findViewById(R.id.stopImage)
        warningImage = view.findViewById(R.id.warningImage)
        goImage = view.findViewById(R.id.goImage)

        loadDurationsFromPreferences()

        redLight.alpha = 0f
        yellowLight.alpha = 0f
        greenLight.alpha = 0f
        stopImage.visibility = View.INVISIBLE
        warningImage.visibility = View.INVISIBLE
        goImage.visibility = View.INVISIBLE

        val settingsButton: View = view.findViewById(R.id.settings_button)
        settingsButton.setOnClickListener {
            showDurationDialog()
        }

        if (!isTrafficCycleStarted) {
            startTrafficLightCycle()
            isTrafficCycleStarted = true
        }

        checkTimeAndActivateYellowLight()

        return view
    }

    @SuppressLint("MissingInflatedId")
    private fun showDurationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Change Light Durations")

        val view = layoutInflater.inflate(R.layout.dialog_duration, null)
        val redLightInput = view.findViewById<EditText>(R.id.redLightDuration)
        val yellowLightInput = view.findViewById<EditText>(R.id.yellowLightDuration)
        val greenLightInput = view.findViewById<EditText>(R.id.greenLightDuration)

        redLightInput.setText((redLightDuration / 1000).toString())
        yellowLightInput.setText((yellowLightDuration / 1000).toString())
        greenLightInput.setText((greenLightDuration / 1000).toString())

        builder.setView(view)

        builder.setPositiveButton("OK") { _, _ ->
            try {
                redLightDuration = (redLightInput.text.toString().toLong() * 1000)
                yellowLightDuration = (yellowLightInput.text.toString().toLong() * 1000)
                greenLightDuration = (greenLightInput.text.toString().toLong() * 1000)

                saveDurationsToPreferences()

                restartTrafficCycle()

                Toast.makeText(requireContext(), "Light durations updated", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Invalid input", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancel", null)

        val dialog = builder.create()

        dialog.setOnShowListener {
            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setTextColor(resources.getColor(R.color.blue))
            positiveButton.setTypeface(poppinsBold)

            val negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            negativeButton.setTextColor(resources.getColor(R.color.red))
            negativeButton.setTypeface(poppinsRegular)
        }

        dialog.show()
    }

    private fun restartTrafficCycle() {
        isTrafficCycleStarted = false

        redLight.alpha = 0f
        yellowLight.alpha = 0f
        greenLight.alpha = 0f

        stopImage.visibility = View.INVISIBLE
        warningImage.visibility = View.INVISIBLE
        goImage.visibility = View.INVISIBLE

        startTrafficLightCycle()
    }

    private fun loadDurationsFromPreferences() {
        val sharedPreferences = requireContext().getSharedPreferences("TrafficLightPreferences", 0)
        redLightDuration = sharedPreferences.getLong("redLightDuration", 2000L)
        yellowLightDuration = sharedPreferences.getLong("yellowLightDuration", 5000L)
        greenLightDuration = sharedPreferences.getLong("greenLightDuration", 8000L)
    }

    private fun saveDurationsToPreferences() {
        val sharedPreferences = requireContext().getSharedPreferences("TrafficLightPreferences", 0)
        with(sharedPreferences.edit()) {
            putLong("redLightDuration", redLightDuration)
            putLong("yellowLightDuration", yellowLightDuration)
            putLong("greenLightDuration", greenLightDuration)
            apply()
        }
    }

    private fun checkTimeAndActivateYellowLight() {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)

        if (currentHour in 0..6) {
            if (!isYellowActivated) {
                startYellowBlinking()
                isYellowActivated = true
            }
        } else {
            if (isYellowActivated) {
                stopYellowBlinking()
                isYellowActivated = false
            }
            if (!isTrafficCycleStarted) {
                startTrafficLightCycle()
                isTrafficCycleStarted = true
            }
        }

        handler.postDelayed({
            checkTimeAndActivateYellowLight()
        }, 1000)
    }

    private fun startYellowBlinking() {
        yellowLight.alpha = 1f
        redLight.alpha = 0f
        greenLight.alpha = 0f

        stopImage.visibility = View.INVISIBLE
        warningImage.visibility = View.VISIBLE
        goImage.visibility = View.INVISIBLE

        handler.postDelayed({
            yellowLight.alpha = 0f
            handler.postDelayed({
                yellowLight.alpha = 1f
                startYellowBlinking()
            }, 1000)
        }, 1000)
    }

    private fun stopYellowBlinking() {
        yellowLight.alpha = 0f
        handler.removeCallbacksAndMessages(null)
        warningImage.visibility = View.INVISIBLE
    }

    private fun startTrafficLightCycle() {
        stopImage.visibility = View.VISIBLE
        warningImage.visibility = View.INVISIBLE
        goImage.visibility = View.INVISIBLE

        redLight.alpha = 1f
        yellowLight.alpha = 0f
        greenLight.alpha = 0f

        handler.postDelayed({
            redLight.alpha = 0f
            yellowLight.alpha = 1f
            greenLight.alpha = 0f

            stopImage.visibility = View.INVISIBLE
            warningImage.visibility = View.VISIBLE
            goImage.visibility = View.INVISIBLE

        }, redLightDuration)

        handler.postDelayed({
            redLight.alpha = 0f
            yellowLight.alpha = 0f
            greenLight.alpha = 1f

            stopImage.visibility = View.INVISIBLE
            warningImage.visibility = View.INVISIBLE
            goImage.visibility = View.VISIBLE

        }, redLightDuration + yellowLightDuration)

        handler.postDelayed({
            redLight.alpha = 1f
            yellowLight.alpha = 0f
            greenLight.alpha = 0f

            stopImage.visibility = View.VISIBLE
            warningImage.visibility = View.INVISIBLE
            goImage.visibility = View.INVISIBLE
        }, redLightDuration + yellowLightDuration + greenLightDuration)
    }
}
