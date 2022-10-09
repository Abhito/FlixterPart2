package com.example.flixterpart2

import android.support.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class SearchNewResult(
    @SerialName("results")
    val result: List<Person>?
)

@Keep
@Serializable
data class Person(
    @SerialName("name")
    val name: String?,
    @SerialName("profile_path")
    val pictureUrl: String?,
    @SerialName("known_for")
    val knownFor: List<getData>?,
) : java.io.Serializable{
    val titletext = knownFor?.firstOrNull{it.title != null}?.title ?: ""
    val posterAddress = "https://image.tmdb.org/t/p/w500/${knownFor?.firstOrNull{it.posterUrl != null}?.posterUrl?: ""}"
    val description = knownFor?.firstOrNull{it.overview != null}?.overview?: ""
}


@Keep
@Serializable
data class getData(
    @SerialName("title")
    val title: String? = null,
    @SerialName("poster_path")
    val posterUrl: String?,
    @SerialName("overview")
    val overview: String?,
) : java.io.Serializable