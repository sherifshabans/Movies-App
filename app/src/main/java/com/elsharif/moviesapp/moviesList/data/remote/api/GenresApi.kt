package com.elsharif.moviesapp.moviesList.data.remote.api


import com.elsharif.moviesapp.moviesList.data.remote.respnod.GenresListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GenresApi {

    @GET("genre/{type}/list")
    suspend fun getGenresList(
        @Path("type") genre: String,
        @Query("api_key") apiKey: String
    ): GenresListDto

}