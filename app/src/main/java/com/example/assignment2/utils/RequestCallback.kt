package com.example.assignment2.utils

import com.example.assignment2.model.MovieModel

interface RequestCallback {
    fun onAPIReturn(movie: MovieModel, count: Int)
}