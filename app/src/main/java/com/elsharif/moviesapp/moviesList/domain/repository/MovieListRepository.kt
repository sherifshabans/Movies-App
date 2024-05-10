package com.elsharif.moviesapp.moviesList.domain.repository

import com.elsharif.moviesapp.moviesList.domain.models.Movie
import com.elsharif.moviesapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {
    suspend fun updateItem(media: Movie)

    suspend fun insertItem(media: Movie)

    suspend fun getItem(
        id: Int,
        type: String,
        category: String
    ): Movie

    suspend fun getMoviesAndTvSeriesList(
        fetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        category: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Movie>>>

    suspend fun getTrendingList(
        fetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        time: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Movie>>>

}