package com.karvinok.composemovies.ui.viewModel

import com.karvinok.composemovies.common.BaseViewModel
import com.karvinok.data.entity.Movie
import com.karvinok.data.repository.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel(
    private val repository: MoviesRepository
): BaseViewModel() {

    val moviesState = MutableStateFlow<List<Movie>>(listOf())

    init {
        requestMovies()
    }

    fun requestMovies() {
        doOnBackground {
            repository.requestMovies(1).either(::handleError){
                moviesState.value = it.results?: listOf()
            }
        }
    }

    fun onBackClicked() {
        closeScreen()
    }
}