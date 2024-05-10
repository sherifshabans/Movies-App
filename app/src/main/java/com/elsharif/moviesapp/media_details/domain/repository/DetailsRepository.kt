package com.elsharif.moviesapp.media_details.domain.repository


import com.elsharif.moviesapp.moviesList.domain.models.Movie
import com.elsharif.moviesapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {

    suspend fun getDetails(
        type: String,
        isRefresh: Boolean,
        id: Int,
        apiKey: String
    ): Flow<Resource<Movie>>

}










