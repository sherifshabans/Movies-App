package com.elsharif.moviesapp.moviesList.data.mappers

import com.elsharif.moviesapp.moviesList.data.local.movie.MovieEntity
import com.elsharif.moviesapp.moviesList.data.remote.respnod.MovieDto
import com.elsharif.moviesapp.moviesList.domain.models.Movie
import com.elsharif.moviesapp.util.Constants


fun MovieDto.toMovieEntity(
    type: String,
    category: String,
): MovieEntity {
    return MovieEntity(
        backdropPath = backdrop_path ?: Constants.unavailable,
        originalLanguage = original_language ?: Constants.unavailable,
        overview = overview ?: Constants.unavailable,
        posterPath = poster_path ?: Constants.unavailable,
        releaseDate = release_date ?: "-1,-2",
        title = title ?: name ?: Constants.unavailable,
        originalName = original_name ?: Constants.unavailable,
        voteAverage = vote_average ?: 0.0,
        popularity = popularity ?: 0.0,
        voteCount = vote_count ?: 0,
        genreIds = try {
            genre_ids?.joinToString(",") ?: "-1,-2"
        } catch (e: Exception) {
            "-1,-2"
        },
        id = id ?: 1,
        adult = adult ?: false,
        mediaType = type,
        category = category,
        originCountry = try {
            origin_country?.joinToString(",") ?: "-1,-2"
        } catch (e: Exception) {
            "-1,-2"
        },
        originalTitle = original_title ?: original_name ?: Constants.unavailable,
        videos = try {
            videos?.joinToString(",") ?: "-1,-2"
        } catch (e: Exception) {
            "-1,-2"
        },
        similarMediaList = try {
            similarMediaList?.joinToString(",") ?: "-1,-2"
        } catch (e: Exception) {
            "-1,-2"
        },
        firstAirDate = first_air_date ?: "",
        video = video ?: false,

        status = "",
        runtime = 0,
        tagline = "",
    )
}


fun MovieDto.toMovie(
    type: String,
    category: String,
): Movie {
    return Movie(
        backdropPath = backdrop_path ?: Constants.unavailable,
        originalLanguage = original_language ?: Constants.unavailable,
        overview = overview ?: Constants.unavailable,
        posterPath = poster_path ?: Constants.unavailable,
        releaseDate = release_date ?: Constants.unavailable,
        title = title ?: name ?: Constants.unavailable,
        voteAverage = vote_average ?: 0.0,
        popularity = popularity ?: 0.0,
        voteCount = vote_count ?: 0,
        genreIds = genre_ids ?: emptyList(),
        id = id ?: 1,
        adult = adult ?: false,
        mediaType = type,
        category = category,
        originCountry = origin_country ?: emptyList(),
        originalTitle = original_title ?: original_name ?: Constants.unavailable,
        runtime = null,
        status = null,
        tagline = null,
        videos = videos,
        similarMediaList = similarMediaList ?: emptyList()
    )
}

fun Movie.toMovieEntity(): MovieEntity {
    return MovieEntity(
        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        genreIds = try {
            genreIds.joinToString(",")
        } catch (e: Exception) {
            "-1,-2"
        },
        id = id,
        adult = adult,
        mediaType = mediaType,
        originCountry = try {
            originCountry.joinToString(",")
        } catch (e: Exception) {
            "-1,-2"
        },
        originalTitle = originalTitle,
        category = category,
        videos = try {
            videos?.joinToString(",") ?: "-1,-2"
        } catch (e: Exception) {
            "-1,-2"
        },
        similarMediaList = try {
            similarMediaList.joinToString(",")
        } catch (e: Exception) {
            "-1,-2"
        },
        video = false,
        firstAirDate = releaseDate,
        originalName = originalTitle,

        status = status ?: "",
        runtime = runtime ?: 0,
        tagline = tagline ?: ""
    )
}




fun MovieEntity.toMovie(
    type: String,
    category: String
): Movie {
    return Movie(
        backdropPath = backdropPath ?: Constants.unavailable,
        originalLanguage = originalLanguage ?: Constants.unavailable,
        overview = overview ?: Constants.unavailable,
        posterPath = posterPath ?: Constants.unavailable,
        releaseDate = releaseDate ?: firstAirDate ?: Constants.unavailable,
        title = title ?: Constants.unavailable,
        voteAverage = voteAverage ?: 0.0,
        popularity = popularity ?: 0.0,
        voteCount = voteCount ?: 0,
        genreIds = try {
            genreIds?.split(",")!!.map { it.toInt() }
        } catch (e: Exception) {
            listOf(-1, -2)
        },
        id = id ?: 1,
        adult = adult ?: false,
        mediaType = type,
        originCountry = try {
            originCountry?.split(",")!!.map { it }
        } catch (e: Exception) {
            listOf("-1", "-2")
        },
        originalTitle = originalTitle ?: originalName ?: Constants.unavailable,
        category = category,
        runtime = runtime ?: 0,
        status = status ?: "",
        tagline = tagline ?: "",
        videos = try {
            videos?.split(",")?.map { it }
        } catch (e: Exception) {
            listOf("-1", "-2")
        },
        similarMediaList = try {
            similarMediaList?.split(",")!!.map { it.toInt() }
        } catch (e: Exception) {
            listOf(-1, -2)
        },
    )
}





















