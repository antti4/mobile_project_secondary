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

/**
 * A method to take in two variables and sendig them as a post request to the given url.
 * The method uses OkHttpClient for the request that does nothing an failure and prints the response
 * on console in case of success.
 * The requestbody is first build with FormBody.builder then the builder.url is given the required
 * attributes for a post request. The client then sends the call request into the queue and calls
 * one of the callback functions fo either successfull connection or a failed connection
 */
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

        /**
         * a function that is called on a succesful httpconnection with the response attribut being
         * the data that is sent back from the url.
         * The method prints the data that has been sent back on the console
         */
        override fun onResponse(call: Call, response: Response) {
            try {
                val responseData = response.body()!!.string()
                println(responseData)

            } catch (e: Exception) {

            }
        }
    })
}