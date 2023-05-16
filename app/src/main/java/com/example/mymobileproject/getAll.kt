package com.example.mymobileproject

import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

    public fun getUrl(url: String, callback : (json: String?)->Unit): Unit {
        val myUrl = URL(url)
        val sb = StringBuffer()
        val urlConnection = myUrl.openConnection() as HttpURLConnection
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
    }
