package com.elsharif.moviesapp.moviesList.domain.repository

import com.elsharif.moviesapp.moviesList.domain.model.Movie
import com.elsharif.moviesapp.moviesList.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {
    suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>>

    suspend fun getMovie(id: Int): Flow<Resource<Movie>>
}