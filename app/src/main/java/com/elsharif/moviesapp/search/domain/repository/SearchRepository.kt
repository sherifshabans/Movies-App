package com.elsharif.moviesapp.search.domain.repository

import com.elsharif.moviesapp.moviesList.domain.models.Movie
import com.elsharif.moviesapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getSearchList(
        fetchFromRemote: Boolean,
        query: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Movie>>>

}










