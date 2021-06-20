package com.example.app

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import io.github.controlwear.virtual.joystick.android.JoystickView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import kotlin.math.cos
import kotlin.math.sin

// TODO: rudder and throttle seekbars, MVVM.
// TODO: remove disconnect button and disconnect automatically?
//TODO: disable joystick when not connected?(not really needed because of try & catch) make knob stay in place? implement our own joystick? (if not, delete Joystick class, xml fragment, xml circles)
//TODO: presentation , video , README , class diagram, txt file with names ids and link to git
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val connect = findViewById<Button>(R.id.connect)
        val disconnect = findViewById<Button>(R.id.disconnect)
        val ip = findViewById<EditText>(R.id.ip)
        val port = findViewById<EditText>(R.id.port)

        var fg : Socket? = null
        var out : PrintWriter? =null
        var input : BufferedReader? = null

        val joystick = findViewById<View>(R.id.joystickView) as JoystickView
        joystick.setOnMoveListener { angle, strength ->
            val thread = Thread {
                try {
                    var aileron = sin(angle.toDouble())*strength/100;
                    var elevator = cos(angle.toDouble())*strength/100;
                    changeValue(out,input,"aileron",aileron.toString())
                    changeValue(out,input,"elevator",elevator.toString())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            thread.start()
        }

        fun start(ip:String,port:Int) {
            println("trying to connect")
            //use IPv4 address, in case of android emulator use 10.0.2.2
            fg = Socket(ip, port)
            println("connected")
            out = PrintWriter(fg!!.getOutputStream(), true)
            input = BufferedReader(InputStreamReader(fg!!.getInputStream()))
            runOnUiThread {
                disconnect.setEnabled(true)
                connect.setEnabled(false)
            }
        }

        fun stop() {
            input!!.close()
            out!!.close()
            fg!!.close()
            println("closed connection")
            runOnUiThread {
                connect.setEnabled(true)
                disconnect.setEnabled(false)
            }
        }

        connect.setOnClickListener{
            val thread = Thread {
                try {
                    start(ip.text.toString(),port.text.toString().toInt())
                } catch (e: Exception) {
                    //TODO: toast fail message
                    e.printStackTrace()
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
    fun changeValue(out:PrintWriter?,input:BufferedReader?,type:String,value:String) {
        println("printing:"+"set /controls/flight/"+type+" "+value+"\r\n")
        out!!.print("set /controls/flight/"+type+" "+value+"\r\n")
        out!!.flush()
        val resp: String = input!!.readLine()
        println("response: "+resp)

    }
}