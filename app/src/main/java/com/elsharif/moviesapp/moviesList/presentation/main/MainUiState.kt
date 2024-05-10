package com.elsharif.moviesapp.moviesList.presentation.main


import com.elsharif.moviesapp.moviesList.domain.models.Genre
import com.elsharif.moviesapp.moviesList.domain.models.Movie

data class MainUiState(

    val popularMoviesPage: Int = 1,
    val topRatedMoviesPage: Int = 1,
    val nowPlayingMoviesPage: Int = 1,

    val popularTvSeriesPage: Int = 1,
    val topRatedTvSeriesPage: Int = 1,

    val trendingAllPage: Int = 1,

    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val areListsToBuildSpecialListEmpty: Boolean = true,

    val popularMoviesList: List<Movie> = emptyList(),
    val topRatedMoviesList: List<Movie> = emptyList(),
    val nowPlayingMoviesList: List<Movie> = emptyList(),

    val popularTvSeriesList: List<Movie> = emptyList(),
    val topRatedTvSeriesList: List<Movie> = emptyList(),

    val trendingAllList: List<Movie> = emptyList(),

    // popularTvSeriesList + topRatedTvSeriesList
    val tvSeriesList: List<Movie> = emptyList(),

    // topRatedTvSeriesList + topRatedMoviesList
    val topRatedAllList: List<Movie> = emptyList(),


    // nowPlayingTvSeriesList + nowPlayingMoviesList
    val recommendedAllList: List<Movie> = emptyList(),


    // matching items in:
    // recommendedAllList and trendingAllList
    val specialList: List<Movie> = emptyList(),

    val moviesGenresList: List<Genre> = emptyList(),
    val tvGenresList: List<Genre> = emptyList(),

    )