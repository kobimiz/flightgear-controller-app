package com.example.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class ButtonsViewModel(private val mainModel: MainModel) : ViewModel() {
    private var fg    : Socket? = null

    private var _disconnectIsEnabled = MutableLiveData(false)
    private var _connectIsEnabled    = MutableLiveData(true)
    private var _joystickIsEnabled   = MutableLiveData(false)
    private var _rudderIsEnabled     = MutableLiveData(false)
    private var _throttleIsEnabled   = MutableLiveData(false)

    val disconnectIsEnabled : LiveData<Boolean> = _disconnectIsEnabled
    val connectIsEnabled    : LiveData<Boolean> = _connectIsEnabled
    val joystickIsEnabled   : LiveData<Boolean> = _joystickIsEnabled
    val rudderIsEnabled     : LiveData<Boolean> = _rudderIsEnabled
    val throttleIsEnabled   : LiveData<Boolean> = _throttleIsEnabled

    private fun start(ip:String, port:Int) {
        println("trying to connect")
        //use IPv4 address, in case of android emulator use 10.0.2.2
        fg = Socket(ip, port)
        println("connected")
        mainModel.out = PrintWriter(fg!!.getOutputStream(), true)
        mainModel.input = BufferedReader(InputStreamReader(fg!!.getInputStream()))
        _disconnectIsEnabled.postValue(true)
        _connectIsEnabled.postValue(false)
        _joystickIsEnabled.postValue(true)
        _rudderIsEnabled.postValue(true)
        _throttleIsEnabled.postValue(true)
    }

    private fun stop() {
        mainModel.input!!.close()
        mainModel.out!!.close()
        fg!!.close()
        println("closed connection")
        _connectIsEnabled.postValue(true)
        _disconnectIsEnabled.postValue(false)
        _joystickIsEnabled.postValue(false)
        _rudderIsEnabled.postValue(false)
        _throttleIsEnabled.postValue(false)
    }
    fun connect(ip:String, port:String) {
        val thread = Thread {
            try {
                val portInt = port.toInt()
                start(ip ,portInt)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
    }

    fun disconnect() {
        val thread = Thread {
            try {
                stop()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
    }
}