package com.example.mymobileproject

import android.app.Activity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
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
    val url : String = "https://dummyjson.com/users"

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class User(var id: Int? = null,
                    var firstName: String? = null,
                    var lastName: String? = null) {
        @Override
        override fun toString() : String{
            return "$id | $firstName | $lastName"
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class UsersJsonObject(var users: MutableList<User>? = null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupButton()
    }

    private fun setupButton() {
        findViewById<Button>(R.id.PostButton)
            .setOnClickListener {
                val fname = findViewById<EditText>(R.id.fName)
                val lname = findViewById<EditText>(R.id.lName)
                thread {
                    postNewUser(fname.text.toString(), lname.text.toString(), url)
                }
            }
    }

    override fun onResume() {
        super.onResume()
        thread {
            publishAllData()
        }
    }
    fun publishAllData(){
        val json = getUrl(url){
            if(it != null){
                val users : MutableList<MainActivity.User>? = outputAllInConsole(it)
                runOnUiThread(){
                    val list : ListView = findViewById(R.id.listView)
                    val adapter = ArrayAdapter<MainActivity.User>(this, R.layout.item, R.id.myTextView, users!!.toTypedArray())
                    list.adapter = adapter
                }
            }
        }
    }
}