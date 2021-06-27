package com.example.app

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import com.example.app.databinding.ActivityMainBinding

class MainViewModel : ViewModel() {
    val mainModel: MainModel = MainModel()
    val buttonsViewModel: ButtonsViewModel = ButtonsViewModel(mainModel)
    val inputsViewModel: InputsViewModel = InputsViewModel()
    val joystickViewModel = JoystickViewModel(mainModel)
    val rudderViewModel = SeekBarViewModel(mainModel, "/controls/flight/","rudder")
    val throttleViewModel = SeekBarViewModel(mainModel,"/controls/engines/current-engine/", "throttle")
}