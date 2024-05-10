package com.elsharif.moviesapp.search.presentation

import com.elsharif.moviesapp.moviesList.domain.models.Movie


data class SearchScreenState(

    val searchPage: Int = 1,

    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,

    val searchQuery: String = "",

    val searchList: List<Movie> = emptyList(),


)