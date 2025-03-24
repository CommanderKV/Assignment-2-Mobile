package com.example.assignment2.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment2.utils.MovieClickListener
import com.example.assignment2.databinding.ActivityMainBinding
import com.example.assignment2.model.MovieModel
import com.example.assignment2.utils.Adapter
import com.example.assignment2.utils.ApiClient
import com.example.assignment2.utils.RequestCallback
import com.example.assignment2.viewModels.MovieViewModel


class MainActivity : AppCompatActivity(), MovieClickListener, RequestCallback {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MovieViewModel
    lateinit var recyclerView: RecyclerView

    lateinit var myAdapter: Adapter

    lateinit var moviesList: ArrayList<MovieModel>
    var addingMovies: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the bindings
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the viewModel
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        // Get the recycler view
        recyclerView = binding.recyclerView

        // Create a linear view manager
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Make a call to the api
        ApiClient().searchMovies("the maze", this, this)


        // Add listeners
        binding.search.setOnClickListener {
            val searchText: String = binding.searchInput.text.toString()
            ApiClient().searchMovies(searchText, this, this)
        }

        // Build temp movies list
        moviesList = arrayListOf(MovieModel("", "", "", "", ""))
    }

    override fun onClick(v: View, pos: Int) {
        // Get the selected movie
        val movie: MovieModel = moviesList[pos]
        val intent: Intent = Intent(this, MovieActivity::class.java).apply {
            putExtra("MOVIETITLE", movie.Title)
            putExtra("MOVIETYPE", movie.Type)
            putExtra("MOVIERELEASEDATE", movie.Released)
            putExtra("MOVIERUNTIME", movie.Runtime)
            putExtra("MOVIERATING", movie.rating)
            putExtra("MOVIEDIRECTOR", movie.Director)
            putExtra("MOVIEPLOT", movie.Plot)
            putExtra("MOVIEPOSTER", movie.Poster)
            putExtra("MOVIEGENRE", movie.Genre)
            putExtra("MOVIELANGUAGE", movie.Language)
        }
        startActivity(intent)
    }

    override fun onAPIReturn(movie: MovieModel, count: Int) {
        println("${addingMovies} -- ${count}")
        // Check if we are done sending movies to the recycler view
        if (count == -1) {
            moviesList.add(movie)
            addingMovies = false

        // Check if we are sending new data to the recycler view
        } else if (count == 0) {
            addingMovies = true
            moviesList = arrayListOf(movie)

        // Add the movies if we are allowed to
        } else if (addingMovies) {
            moviesList.add(movie)
        }

        // Reset the recycler and adapter
        myAdapter = Adapter(moviesList)
        recyclerView.adapter = myAdapter
        myAdapter.clickListener = this
    }
}