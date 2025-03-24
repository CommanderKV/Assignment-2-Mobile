package com.example.assignment2.views

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.assignment2.databinding.ActivityMainBinding
import com.example.assignment2.databinding.ActivityMovieBinding
import com.example.assignment2.model.MovieModel
import com.example.assignment2.utils.ApiClient
import com.example.assignment2.utils.RequestCallback
import com.example.assignment2.viewModels.MovieViewModel

class MovieActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMovieBinding
    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the movie title
        var movieTitle = intent.getStringExtra("MOVIETITLE")?: "Error getting movie title"
        var movieType = intent.getStringExtra("MOVIETYPE")?: "Error getting movie type"
        var movieReleaseDate = intent.getStringExtra("MOVIERELEASEDATE")?: "Error getting release date"
        var movieRuntime = intent.getStringExtra("MOVIERUNTIME")?: "Error getting movie runtime"
        var movieRating = intent.getStringExtra("MOVIERATING")?: "Error getting movie rating"
        var movieDirector = intent.getStringExtra("MOVIEDIRECTOR")?: "Error getting director"
        var moviePlot = intent.getStringExtra("MOVIEPLOT")?: "Error getting plot"
        var moviePoster = intent.getStringExtra("MOVIEPOSTER")?: ""
        var movieGenre = intent.getStringExtra("MOVIEGENRE")?: "Error getting genre"
        var movieLanguage = intent.getStringExtra("MOVIELANGUAGE")?: "Error getting language"

        // Observe the movie details
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        // Set the movie in the view Model
        viewModel.setMovieModel(
            movieTitle,
            movieType,
            movieReleaseDate,
            movieRuntime,
            movieRating,
            movieDirector,
            moviePlot,
            moviePoster,
            movieGenre,
            movieLanguage
        )

        // Fetch the movie
        viewModel.getMovieModel()

        viewModel.getMovieModel().observe(this, Observer { movie ->
            movie?.let {
                if (it.Poster.contains("http")) {
                    ApiClient().loadImageFromUrl(binding.movieLogo, it.Poster)
                }
                binding.movieTitle.text = it.Title
                binding.type.text = "Type: ${it.Type}"
                binding.releaseDate.text = it.Released
                binding.runtime.text = it.Runtime
                binding.movieRating.text = it.rating
                binding.director.text = "Director: ${it.Director}"
                binding.genre.text = "Genre(s): ${it.Genre}"
                binding.languge.text = "Language(s): ${it.Language}"
                binding.plot.text = it.Plot
            }
        })

        binding.backButton.setOnClickListener {
            finish()
        }
    }
}