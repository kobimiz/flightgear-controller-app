package com.example.app

class JoystickViewModel(var mainModel: MainModel) : io.github.controlwear.virtual.joystick.android.JoystickView.OnMoveListener {
    override fun onMove(angle: Int, strength: Int) {
        val thread = Thread {
            try {
                //if(fg!=null) {
                println("angle: $angle")
                println("strength: $strength")
                val aileron = Math.cos(Math.toRadians(angle.toDouble())) * strength / 100.0
                val elevator = Math.sin(Math.toRadians(angle.toDouble())) * strength / 100.0
                mainModel.changeValue("/controls/flight/", "aileron", aileron.toString())
                mainModel.changeValue("/controls/flight/", "elevator", elevator.toString())
                //}
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
    }
}