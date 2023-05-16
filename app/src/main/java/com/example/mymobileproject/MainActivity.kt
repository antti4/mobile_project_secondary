package com.example.mymobileproject

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class User(var id: Int? = null,
                    var firstName: String? = null,
                    var lastName: String? = null)

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class UsersJsonObject(var users: MutableList<User>? = null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        /*downloadUrlAsync(this, "https://dummyjson.com/users") {
            println(it)
        }*/
        thread {
            val json = getUrl("https://dummyjson.com/users")
            if(json != null){
                outputAllInConsole(json)
                /*runOnUiThread(){
                    val text: TextView = findViewById(R.id.contentText)
                    text.setText(parseJson(json))
                }*/
            }
        }
    }
    //05-06
    fun outputAllInConsole(stuff : String?){
        val mp = ObjectMapper()
        val myObject: UsersJsonObject = mp.readValue(stuff, UsersJsonObject::class.java)
        val users: MutableList<User>? = myObject.users
        users?.forEach {
            println(it)
        }
        runOnUiThread(){
            val list : ListView = findViewById(R.id.listView)
            val adapter = ArrayAdapter<User>(this, R.layout.item, R.id.myTextView, users!!.toTypedArray())
            list.adapter = adapter
        }
    }
    //04
    fun parseJson(json : String) : String?{
        val jsonObject = JSONObject(json)
        return jsonObject.getJSONArray("users").getJSONObject(0).getString("name")
    }
    //02-03
    fun getUrl(url: String) : String? {
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
        return sb.toString()
    }

    //07
    /*fun downloadUrlAsync(context : Activity, url: String, callback : (json: String?)->Unit){
        thread{
            val json = getUrl(url)
            context.runOnUiThread(){
                callback(json)
            }
        }
    }*/
}