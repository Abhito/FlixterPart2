package com.example.flixterpart2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.flixterpart2.databinding.ActivityMainBinding
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONException


fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

private const val TAG = "MainActivity/"
private const val SEARCH_API_KEY = BuildConfig.API_KEY
private const val PERSON_SEARCH_URL = "https://api.themoviedb.org/3/person/popular?api_key=${SEARCH_API_KEY}"

class MainActivity : AppCompatActivity() {
    private lateinit var peopleRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private val people = mutableListOf<Person>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        peopleRecyclerView = findViewById(R.id.peopleList)

        val personAdapter = PersonAdapter(this, people)
        peopleRecyclerView.adapter = personAdapter

        peopleRecyclerView.layoutManager = GridLayoutManager(this, 2)

        val client = AsyncHttpClient()
        client.get(PERSON_SEARCH_URL, object: JsonHttpResponseHandler(){
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch people: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG, "Successfully fetched person: $json")
                try{
                    val parsedJson = createJson().decodeFromString(
                        SearchNewResult.serializer(),
                        json.jsonObject.toString()
                    )
                    parsedJson.result?.let{list ->
                        people.addAll(list)
                    }

                    personAdapter.notifyDataSetChanged()
                } catch(e: JSONException){
                    Log.e(TAG, "Exception: $e")
                }
            }
        })
    }
}