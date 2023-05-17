package com.example.mymobileproject

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import com.fasterxml.jackson.module.kotlin.*


fun postNewUser(fName : String, lName : String, url : String) {
        val sb = StringBuffer()
        val url = URL("$url/add")
        val httpURLConnection = url.openConnection() as HttpsURLConnection
        val user = "[{\"firstName\": \"$fName\", \"lastName\": \"$lName\"}]"
        httpURLConnection.requestMethod = "POST"
        httpURLConnection.setRequestProperty("Content-Type", "application/json")
        httpURLConnection.setRequestProperty("Accept", "application/json")
        httpURLConnection.doOutput = true
        try {
            val outputStreamWriter = BufferedWriter(OutputStreamWriter(httpURLConnection.outputStream, "UTF-8"))
            outputStreamWriter.write(user)
            outputStreamWriter.flush()
            println(httpURLConnection.responseMessage)
            println(user)
            val myStream = BufferedInputStream(httpURLConnection.inputStream)
            val reader = BufferedReader(InputStreamReader(myStream))
            reader.use{
                var line :String? = null
                do{
                    line = it.readLine()
                    sb.append(line)
                }while(line != null)
            }
            println(sb)

        }finally {
            httpURLConnection.disconnect()
        }
}

