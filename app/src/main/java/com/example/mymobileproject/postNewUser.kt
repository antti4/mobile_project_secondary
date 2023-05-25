package com.example.mymobileproject

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import com.fasterxml.jackson.module.kotlin.*
import okhttp3.*

fun postNewUser(fName: String, lName: String, url: String){
    var json = FormBody.Builder()
        .add("firstName", fName)
        .add("lastName", lName)
        .build()
    var client = OkHttpClient()
    val request = Request.Builder().url("$url/add")
        .post(json)
        .addHeader("Content-Type", "application/json")
        .addHeader("Accept", "application/json")
        .build()
    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            TODO("Not implemented")
        }

        override fun onResponse(call: Call, response: Response) {
            try {
                val responseData = response.body()!!.string()
                println(responseData)

            } catch (e: Exception) {

            }
        }
    })
}