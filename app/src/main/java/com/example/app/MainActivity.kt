package com.example.app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.io.*
import java.net.Socket


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val connect = findViewById<Button>(R.id.connect)
        val change = findViewById<Button>(R.id.change)
        val disconnect = findViewById<Button>(R.id.disconnect)
        val valuetext = findViewById<EditText>(R.id.valuetext)
        val valuenametext = findViewById<EditText>(R.id.valuenametext)

        var fg : Socket? = null
        var out : PrintWriter? =null
        var input : BufferedReader? = null

        fun start() {
            println("trying to connect")
            fg = Socket("10.0.2.2", 6400)
            println("connected")
            out = PrintWriter(fg!!.getOutputStream(), true)
            input = BufferedReader(InputStreamReader(fg!!.getInputStream()))
        }

        fun stop() {
            input!!.close()
            out!!.close()
            fg!!.close()
            println("closed connection")
        }

        fun changeValue(type:String,value:String) {
            println("printing:"+"set /controls/flight/"+type+" "+value+"\r\n")
            out!!.print("set /controls/flight/"+type+" "+value+"\r\n")
            out!!.flush()
            val resp: String = input!!.readLine()
            println("response: "+resp)

        }

        connect.setOnClickListener{
            val thread = Thread {
                try {
                    start()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            thread.start()

        }
        change.setOnClickListener{
            val thread = Thread {
                try {
                    val valuename = valuenametext.text.toString()
                    val value = valuetext.text.toString()
                    changeValue(valuenametext.text.toString(),valuetext.text.toString())

                } catch (e: Exception) {
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
}