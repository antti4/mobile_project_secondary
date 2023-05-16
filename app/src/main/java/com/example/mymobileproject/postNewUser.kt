package com.example.mymobileproject

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL


fun postNewUser(fName : String, lName : String, url : String) {
    val json = jsonToString(MainActivity.User(0, fName, lName)) {
        val myUrl = URL("$url/add/")
        println("$url/add")
        val sb = StringBuffer()
        val url = URL("$url/add/")
        val httpURLConnection = url.openConnection() as HttpURLConnection
        val jsonObject = JSONObject()
        jsonObject.put("firstName", "Jack")
        jsonObject.put("lastName", "Danny")
        httpURLConnection.requestMethod = "POST"
        httpURLConnection.setRequestProperty("Content-Type", "application/json")
        httpURLConnection.setRequestProperty("Accept", "application/json")
        httpURLConnection.doInput = true
        httpURLConnection.doOutput = true
        try {
            val outputStreamWriter = OutputStreamWriter(httpURLConnection.outputStream)
            outputStreamWriter.write(jsonObject.toString())
            outputStreamWriter.flush()
            println(httpURLConnection.responseCode)
        }finally {
            httpURLConnection.disconnect()
        }

    }
}

fun jsonToString(user : MainActivity.User, callback : (json: String?)->Unit){
    val mapper = ObjectMapper()
    try {
        val json = mapper.writeValueAsString(user)
        println("ResultingJSONstring = $json")
        callback(json)
    } catch (e: JsonProcessingException) {
        e.printStackTrace()
    }
}