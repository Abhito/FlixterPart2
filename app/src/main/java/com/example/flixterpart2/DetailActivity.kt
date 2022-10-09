package com.example.flixterpart2

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

private const val TAG = "DetailActivity"

class DetailActivity : AppCompatActivity(){
    private lateinit var profileImageView: ImageView
    private lateinit var posterImageView: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var titleTextView: TextView
    private lateinit var overviewTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        nameTextView = findViewById(R.id.personName)
        titleTextView = findViewById(R.id.movieTitle)
        overviewTextView = findViewById(R.id.personDescription)
        profileImageView = findViewById(R.id.personImage)
        posterImageView = findViewById(R.id.movieImage)

        val person = intent.getSerializableExtra(PERSON_EXTRA) as Person

        nameTextView.text = person.name
        titleTextView.text = person.titletext
        overviewTextView.text = person.description

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500/" + person.pictureUrl)
            .into(profileImageView)
        Glide.with(this)
            .load(person.posterAddress)
            .into(posterImageView)

    }
}