package com.example.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.app.databinding.ActivityMainBinding

// TODO: MVVM.
//TODO: use binding instead?
//TODO: delete useless imports
// TODO: customize JoyStick
// TODO: remove disconnect button and disconnect automatically? (& onstop, ondestory)

// TODO: presentation , video , README , class diagram, txt file with names ids and link to git
class MainActivity : AppCompatActivity() {
    private var mainModel: MainModel = MainModel()
    private var buttonsViewModel: ButtonsViewModel = ButtonsViewModel(mainModel)
    private var inputsViewModel: InputsViewModel = InputsViewModel()

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

        /**
         * Setting up view models
         */
        binding.buttonsViewModel = buttonsViewModel
        binding.inputsViewModel = inputsViewModel
        binding.lifecycleOwner = this

        /**
         * move listeners
         */
        val joystickListener = JoystickViewModel(mainModel)
        val rudderListener = SeekBarViewModel(mainModel, "/controls/flight/","rudder")
        val throttleListener = SeekBarViewModel(mainModel,"/controls/engines/current-engine/", "throttle")
        joystick.setOnMoveListener(joystickListener)
        rudder.setOnSeekBarChangeListener(rudderListener)
        throttle.setOnSeekBarChangeListener(throttleListener)
    }
}