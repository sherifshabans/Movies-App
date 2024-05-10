package com.elsharif.moviesapp.moviesList.data.repository

import com.elsharif.moviesapp.moviesList.domain.repository.MovieListRepository
import com.elsharif.moviesapp.moviesList.data.local.movie.MovieDatabase
import com.elsharif.moviesapp.moviesList.data.mappers.toMovie
import com.elsharif.moviesapp.moviesList.data.mappers.toMovieEntity
import com.elsharif.moviesapp.moviesList.data.remote.api.MovieApi
import com.elsharif.moviesapp.moviesList.domain.models.Movie
import com.elsharif.moviesapp.util.Constants
import com.elsharif.moviesapp.util.Constants.TRENDING
import com.elsharif.moviesapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject


class MovieListRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    mediaDb: MovieDatabase
) : MovieListRepository {

    private val mediaDao = mediaDb.movieDao

    override suspend fun insertItem(media: Movie) {
        val mediaEntity = media.toMovieEntity()

        mediaDao.insertMediaItem(
            mediaItem = mediaEntity
        )
    }

    override suspend fun getItem(
        id: Int,
        type: String,
        category: String,
    ): Movie {
        return mediaDao.getMediaById(id).toMovie(
            category = category,
            type = type
        )
    }

    override suspend fun updateItem(media: Movie) {
        val mediaEntity = media.toMovieEntity()

        mediaDao.updateMediaItem(
            mediaItem = mediaEntity
        )

    }

    override suspend fun getMoviesAndTvSeriesList(
        fetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        category: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Movie>>> {
        return flow {

            emit(Resource.Loading(true))

            val localMediaList = mediaDao.getMediaListByTypeAndCategory(type, category)

            val shouldJustLoadFromCache =
                localMediaList.isNotEmpty() && !fetchFromRemote && !isRefresh
            if (shouldJustLoadFromCache) {

                emit(Resource.Success(
                    data = localMediaList.map {
                        it.toMovie(
                            type = type,
                            category = category
                        )
                    }
                ))

                emit(Resource.Loading(false))
                return@flow
            }

            var searchPage = page
            if (isRefresh) {
                mediaDao.deleteMediaByTypeAndCategory(type, category)
                searchPage = 1
            }

            val remoteMediaList = try {
                movieApi.getMoviesAndTvSeriesList(
                    type, category, searchPage, apiKey
                ).results
            } catch (e: java.io.IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                emit(Resource.Loading(false))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                emit(Resource.Loading(false))
                return@flow
            }

            remoteMediaList.let { mediaList ->
                val media = mediaList.map {
                    it.toMovie(
                        type = type,
                        category = category
                    )
                }

                val entities = mediaList.map {
                    it.toMovieEntity(
                        type = type,
                        category = category,
                    )
                }

                mediaDao.insertMediaList(entities)

                emit(
                    Resource.Success(data = media)
                )
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getTrendingList(
        fetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        time: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Movie>>> {
        return flow {

            emit(Resource.Loading(true))

            val localMediaList = mediaDao.getTrendingMediaList(TRENDING)

            val shouldJustLoadFromCache = localMediaList.isNotEmpty() && !fetchFromRemote
            if (shouldJustLoadFromCache) {

                emit(Resource.Success(
                    data = localMediaList.map {
                        it.toMovie(
                            type = it.mediaType ?: Constants.unavailable,
                            category = TRENDING
                        )
                    }
                ))

                emit(Resource.Loading(false))
                return@flow
            }

            var searchPage = page

            if (isRefresh) {
                mediaDao.deleteTrendingMediaList(TRENDING)
                searchPage = 1
            }

            val remoteMediaList = try {
                movieApi.getTrendingList(
                    type, time, searchPage, apiKey
                ).results
            } catch (e: java.io.IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                emit(Resource.Loading(false))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                emit(Resource.Loading(false))
                return@flow
            }

            remoteMediaList.let { mediaList ->

                val media = mediaList.map {
                    it.toMovie(
                        type = it.media_type ?: Constants.unavailable,
                        category = TRENDING
                    )
                }

                val entities = mediaList.map {
                    it.toMovieEntity(
                        type = it.media_type ?: Constants.unavailable,
                        category = TRENDING
                    )
                }

                mediaDao.insertMediaList(entities)

                emit(
                    Resource.Success(data = media)
                )
                emit(Resource.Loading(false))
            }
        }
    }

}



















