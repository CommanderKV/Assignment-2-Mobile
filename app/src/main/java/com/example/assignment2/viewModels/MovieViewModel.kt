package com.example.assignment2.viewModels

import android.content.Context
import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.assignment2.model.MovieModel
import com.example.assignment2.utils.ApiClient
import com.example.assignment2.utils.RequestCallback
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.IOException

class MovieViewModel() : ViewModel() {

    private val movieData = MutableLiveData<MovieModel>()

    fun getMovieModel(): LiveData<MovieModel> {
        return this.movieData
    }

    fun setMovieModel(title: String, type: String, releaseDate: String, runtime: String, rating: String, director: String, plot: String, poster: String, genre: String, language: String) {
        val movie = MovieModel(
            Title=title,
            Released=releaseDate,
            Year=releaseDate,
            Runtime=runtime,
            rating=rating,
            Director=director,
            Plot=plot,
            Poster=poster,
            Genre = genre,
            Language = language,
            imdbID="",
            Type=type
        )
        movieData.value = movie
    }
}