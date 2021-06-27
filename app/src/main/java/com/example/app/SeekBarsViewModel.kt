package com.example.app

import android.widget.SeekBar

class SeekBarViewModel(val mainModel: MainModel, val location : String, val type: String) : SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(
        seek: SeekBar,
        progress: Int, fromUser: Boolean
    ) {
        val half = seek.max/2
        val prog : Double = ((progress.toDouble()-half.toDouble())/half.toDouble())
        val thread = Thread {
            try {
                mainModel.changeValue(location, type, prog.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
    }
}

