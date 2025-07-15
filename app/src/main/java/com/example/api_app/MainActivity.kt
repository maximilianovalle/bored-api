package com.example.api_app

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Call
import okhttp3.Headers
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var activityText: TextView
    private lateinit var activityType: TextView
    private lateinit var activityParticipants: TextView
    private lateinit var activityAccessibility: TextView
    private lateinit var getStartedLink: TextView
    private lateinit var nextActivity: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fetchArtwork()
        nextActivity = findViewById(R.id.nextActivity)
        setupButton(nextActivity)
    }

    private fun fetchArtwork() {
        val client = AsyncHttpClient()
        client["https://bored-api.appbrewery.com/random", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                activityText = findViewById(R.id.activityText)
                activityType = findViewById(R.id.activityType)
                activityParticipants = findViewById(R.id.activityParticipants)
                activityAccessibility = findViewById(R.id.activityAccessibility)
                getStartedLink = findViewById(R.id.getStartedLink)

                Log.d("Activity", "Response successful: $json")

                val activityTextJSON = json.jsonObject.getString("activity")
                val activityTypeJSON = json.jsonObject.getString("type")
                val activityAccessibilityJSON = json.jsonObject.getString("accessibility")
                val activityParticipantsJSON = json.jsonObject.getString("participants")
                val getStartedLinkJSON = json.jsonObject.getString("link")

                activityText.text = activityTextJSON
                activityType.text = activityTypeJSON
                activityAccessibility.text = activityAccessibilityJSON
                activityParticipants.text = activityParticipantsJSON
            }

            override fun onFailure(statusCode: Int, headers: Headers?, errorResponse: String, throwable: Throwable?) {
                Log.d("Activity Error", errorResponse)
            }
        }]
    }

    private fun setupButton(button: Button) {
        button.setOnClickListener {
            fetchArtwork()
        }
    }
}