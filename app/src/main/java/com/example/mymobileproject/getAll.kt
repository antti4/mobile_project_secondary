package com.example.mymobileproject

import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * A method to create a connection to the backend with the given url, retrieving it's data and
 * then calling the callback method with the given json data as it's attribute
 * The method uses HttpURLConnection as the connector and stream readers in order to turn the bytes
 * into json. The json is built with a string buffer one row at a time and given as an attribute to
 * the callback that is called
 */
public fun getUrl(url: String, callback : (json: String?)->Unit): Unit {
        val myUrl = URL(url)
        val sb = StringBuffer()
        val urlConnection = myUrl.openConnection() as HttpURLConnection
        try {
            val myStream = BufferedInputStream(urlConnection.inputStream)
            val reader = BufferedReader(InputStreamReader(myStream))
            reader.use{
                var line :String? = null
                do{
                    line = it.readLine()
                    sb.append(line)
                }while(line != null)
            }
            callback(sb.toString())
        }finally {
            urlConnection.disconnect()
        }
}
