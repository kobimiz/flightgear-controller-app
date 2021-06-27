package com.example.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var mainViewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * CONSTANTS
         */
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val joystick = binding.joystickView
        val rudder = binding.rudder
        val throttle = binding.throttle

        binding.buttonsViewModel = mainViewModel.buttonsViewModel
        binding.inputsViewModel = mainViewModel.inputsViewModel
        binding.lifecycleOwner = this

        /**
         * move listeners
         */
        joystick.setOnMoveListener(mainViewModel.joystickViewModel)
        rudder.setOnSeekBarChangeListener(mainViewModel.rudderViewModel)
        throttle.setOnSeekBarChangeListener(mainViewModel.throttleViewModel)
    }
}