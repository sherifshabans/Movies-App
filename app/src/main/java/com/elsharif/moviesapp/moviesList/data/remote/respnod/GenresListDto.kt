package com.elsharif.moviesapp.moviesList.data.remote.respnod

import com.elsharif.moviesapp.moviesList.domain.models.Genre


data class GenresListDto(
    val genres: List<Genre>
)