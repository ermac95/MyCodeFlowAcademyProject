package com.mycodeflow.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycodeflow.data.*
import com.mycodeflow.repository.MovieDetailRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
        val movieDetailRepository: MovieDetailRepository
) : ViewModel() {
    private var mutableMovie = MutableLiveData<MovieDetailModel>()
    val movieExample: LiveData<MovieDetailModel> get() = mutableMovie

    fun updateMovieDetails(movieId: Int){
        viewModelScope.launch {
            mutableMovie.value = movieDetailRepository.getMovieById(movieId)
        }
    }
}