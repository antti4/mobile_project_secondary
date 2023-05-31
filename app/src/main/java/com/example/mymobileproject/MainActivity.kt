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

/**
 * the main class of the app that extends the appCompatActivity class
 */
class MainActivity : AppCompatActivity() {
    val url : String = "https://dummyjson.com/users"

    /**
     * A data class that is used for parsing json into an User object
     * It is given the JsonIgnoreProperties in order to be able to ignore all the unneeded data
     * given from a json
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class User(var id: Int? = null,
                    var firstName: String? = null,
                    var lastName: String? = null) {
        @Override
        override fun toString() : String{
            return "$id | $firstName | $lastName"
        }
    }

    /**
     * A data class for parsing a json with multible objects in it into an User object array
     * It is given the JsonIgnoreProperties in order to be able to ignore all the unneeded data
     * given from a json
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class UsersJsonObject(var users: MutableList<User>? = null)

    /**
     * A method called when the app activity is created.
     * sets the view in the layout and calls the method: setupButton
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupButton()
    }

    /**
     * A method to set a button with the id PostButton in the xml-file
     * An event listener is added to the button that calls the method: postNewUser
     * with variables id:d fName and lName in the xml-file as attributes
     */
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

    /**
     * A method that the method publishAllData on a different thread when activity goes
     * through the resume -state
     */
    override fun onResume() {
        super.onResume()
        thread {
            publishAllData()
        }
    }

    /**
     * A method to fetch data from the specified url and to display it in listView
     * The data is fetched in json format with the getUrl method and is turned into a
     * mutableList<User> with the outputAllInConsole method
     * then the array is displayed in a listView using an ArrayAdapter that is run on the User
     * interface thread
     */
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