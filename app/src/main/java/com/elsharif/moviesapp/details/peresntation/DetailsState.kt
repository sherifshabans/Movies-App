package com.elsharif.moviesapp.details.peresntation

import com.elsharif.moviesapp.moviesList.domain.model.Movie


data class DetailsState(
    val isLoading: Boolean = false,
    val movie: Movie? = null
)
