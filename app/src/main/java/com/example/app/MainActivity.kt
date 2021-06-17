package com.example.app

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.io.*
import java.net.Socket


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val thread = Thread {
            try {
                println("trying to connect")
                val fg = Socket("10.0.0.10", 6400)
                println("connected")
                // FILE IS NOT FOUND
                val input = BufferedReader(FileReader("reg_flight.csv"))
                val out = PrintWriter(fg.getOutputStream())
                var line: String
                while(true) {
                    out.flush()
                    line = input.readLine()
                    if (line == null)
                        break
                }
                out.close()
                input.close()
                fg.close()

//                Log.d("TAG", "trying to connect to 10.0.0.10")
//                val fg = Socket("10.0.0.10", 6400)
//                fg.outputStream.write("get /controls/flight/aileron[0]".toByteArray())
////                val out = PrintWriter(fg.getOutputStream(), true)
////                out.println("get /controls/flight/aileron[0]")
//
//                val reader = BufferedReader(
//                    InputStreamReader(fg.getInputStream())
//                )
//                val str = reader.readLine()
//                println("Input Stream: $str")
//                println("after read")

//                println("Input Stream: $fg.getInputStream().read()")
//
//                var readChar: Int
//                while (true) {
//                    readChar = fg.getInputStream().read()

//                    if (readChar <= -1)
//                        break
//                }

//                try {
//                    val inputStream = DataInputStream(
//                        BufferedInputStream(fg.getInputStream()))
//
//                    println("Input Stream: ${inputStream.readInt()}")
//                    println("after read")
//                } catch (e : java.lang.Exception) {
//                    e.printStackTrace()
//                }
                fg.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        button.setOnClickListener{
            thread.start()
        }
    }
}