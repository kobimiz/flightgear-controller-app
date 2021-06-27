package com.example.app

import java.io.BufferedReader
import java.io.PrintWriter

class MainModel {
    var out   : PrintWriter?    = null
    var input : BufferedReader? = null

    fun changeValue(location:String, type:String, value:String) {
        println("printing: set $location$type $value\r\n")
        out!!.print("set $location$type $value\r\n")
        out!!.flush()
        val resp: String = input!!.readLine()
        println("response: $resp")
    }
}