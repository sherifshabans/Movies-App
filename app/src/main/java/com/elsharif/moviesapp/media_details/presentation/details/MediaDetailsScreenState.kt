package com.elsharif.moviesapp.media_details.presentation.details
import com.elsharif.moviesapp.moviesList.domain.models.Genre
import com.elsharif.moviesapp.moviesList.domain.models.Movie

data class MediaDetailsScreenState(

    val isLoading: Boolean = false,

    val media: Movie? = null,

    val videoId: String = "",
    val readableTime: String = "",

    val similarMediaList: List<Movie> = emptyList(),
    val smallSimilarMediaList: List<Movie> = emptyList(),

    val recommendationsMediaList: List<Movie> = emptyList(),
    val smallRecommendationsMediaList: List<Movie> = emptyList(),

    val videosList: List<String> = emptyList(),
    val moviesGenresList: List<Genre> = emptyList(),
    val tvGenresList: List<Genre> = emptyList()

)