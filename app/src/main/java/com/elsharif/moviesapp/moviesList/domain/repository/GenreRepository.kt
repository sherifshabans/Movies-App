package com.elsharif.moviesapp.moviesList.domain.repository

import com.elsharif.moviesapp.moviesList.domain.models.Genre
import com.elsharif.moviesapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface GenreRepository {
    suspend fun getGenres(
        fetchFromRemote: Boolean,
        type: String,
        apiKey: String
    ): Flow<Resource<List<Genre>>>
}










