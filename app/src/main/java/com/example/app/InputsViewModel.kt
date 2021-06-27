package com.example.app

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InputsViewModel : ViewModel() {
    var ip   = MutableLiveData("")
    var port = MutableLiveData("")
}