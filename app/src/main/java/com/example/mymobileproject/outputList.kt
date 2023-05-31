package com.example.mymobileproject

import android.widget.ArrayAdapter
import android.widget.ListView
import com.fasterxml.jackson.databind.ObjectMapper

/**
 * A method to return a mutableList of User objects
 * The Method uses an ObjectMapper to read the give string and turning it from a json format into
 * user objects and then adding those Objects into UsersJsonObject. it then returns the list
 * with the given data turned into objects or if the data is incompatible with the mapper it returns
 * an empty list
 */
fun outputAllInConsole(stuff : String?) : MutableList<MainActivity.User>?{
    val mp = ObjectMapper()
    val myObject: MainActivity.UsersJsonObject = mp.readValue(stuff, MainActivity.UsersJsonObject::class.java)
    return myObject.users
}