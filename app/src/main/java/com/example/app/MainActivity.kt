package com.example.app

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.app.databinding.ActivityMainBinding
import io.github.controlwear.virtual.joystick.android.JoystickView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import kotlin.math.cos
import kotlin.math.sin

// TODO: MVVM.
//TODO: use binding instead?
//TODO: delete useless imports
// TODO: customize JoyStick
// TODO: remove disconnect button and disconnect automatically? (& onstop, ondestory)

// TODO: presentation , video , README , class diagram, txt file with names ids and link to git
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        /**
         * CONSTANTS
         */
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val connect = binding.connect
        val disconnect = binding.disconnect
        val ip = binding.ip
        val port = binding.port
        val joystick = binding.joystickView
        val rudder = binding.rudder
        val throttle = binding.throttle

        /**
         * VARIABLES
         */
        var fg : Socket? = null
        var out : PrintWriter? =null
        var input : BufferedReader? = null


        //TODO: take care of these through xml
        //initially disabling usage of the joystick and seekbars
        joystick.isEnabled = false
        rudder.isEnabled = false
        throttle.isEnabled = false

        /**
         * move listeners
         */
        joystick.setOnMoveListener { angle, strength : Int ->
            val thread = Thread {
                try {
                    //if(fg!=null) {
                    println("angle: $angle")
                    println("strength: $strength")
                    val aileron = Math.cos(Math.toRadians(angle.toDouble())) * strength / 100.0
                    val elevator = Math.sin(Math.toRadians(angle.toDouble())) * strength / 100.0
                    changeValue(out, input,"/controls/flight/", "aileron", aileron.toString())
                    changeValue(out, input,"/controls/flight/", "elevator", elevator.toString())
                    //}
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            thread.start()
        }

        rudder.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seek: SeekBar,
                progress: Int, fromUser: Boolean
            ) {
                val half = seek.max/2
                val prog : Double = ((progress.toDouble()-half.toDouble())/half.toDouble())
                val thread = Thread {
                    try {
                        changeValue(out, input,"/controls/flight/", "rudder", prog.toString())
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
        })

        throttle.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seek: SeekBar,
                progress: Int, fromUser: Boolean
            ) {
                val half = seek.max/2
                val prog : Double = ((progress.toDouble()-half.toDouble())/half.toDouble())
                val thread = Thread {
                    try {
                        changeValue(out, input,"/controls/engines/current-engine/", "throttle", prog.toString())
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
        })

        /**
         * functions
         */
            fun start(ip:String,port:Int) {
            println("trying to connect")
            //use IPv4 address, in case of android emulator use 10.0.2.2
            fg = Socket(ip, port)
            println("connected")
            out = PrintWriter(fg!!.getOutputStream(), true)
            input = BufferedReader(InputStreamReader(fg!!.getInputStream()))
            runOnUiThread {
                //TODO: take care of these through xml
                disconnect.isEnabled = true
                connect.isEnabled = false
                joystick.isEnabled = true
                rudder.isEnabled = true
                throttle.isEnabled = true

            }
        }

        fun stop() {
            input!!.close()
            out!!.close()
            fg!!.close()
            println("closed connection")
            runOnUiThread {
                //TODO: take care of these through xml
                connect.isEnabled = true
                disconnect.isEnabled = false
                joystick.isEnabled = false
                rudder.isEnabled = false
                throttle.isEnabled = false
            }
        }

        connect.setOnClickListener{
            val thread = Thread {
                try {
                    start(ip.text.toString(),port.text.toString().toInt())
                } catch (e: Exception) {
                    e.printStackTrace()
                    runOnUiThread{
                        Toast.makeText(applicationContext, "couldn't connect - please recheck ip and port.", Toast.LENGTH_LONG).show()
                    }
                }
            }
            thread.start()

        }
        disconnect.setOnClickListener{
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
    private fun changeValue(out:PrintWriter?,input:BufferedReader?,location:String,type:String,value:String) {
        println("printing: set $location$type $value\r\n")
        out!!.print("set $location$type $value\r\n")
        out.flush()
        val resp: String = input!!.readLine()
        println("response: $resp")
    }
}