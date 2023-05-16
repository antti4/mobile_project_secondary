package com.example.mymobileproject

import android.widget.ArrayAdapter
import android.widget.ListView
import com.fasterxml.jackson.databind.ObjectMapper

fun outputAllInConsole(stuff : String?) : MutableList<MainActivity.User>?{
    val mp = ObjectMapper()
    val myObject: MainActivity.UsersJsonObject = mp.readValue(stuff, MainActivity.UsersJsonObject::class.java)
    val users: MutableList<MainActivity.User>? = myObject.users
    users?.forEach {
        println(it)
    }
    return users
}