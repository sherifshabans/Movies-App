package com.elsharif.moviesapp.media_details.data.repository

import com.elsharif.moviesapp.media_details.data.remote.api.ExtraDetailsApi
import com.elsharif.moviesapp.media_details.domain.models.Cast
import com.elsharif.moviesapp.media_details.domain.repository.ExtraDetailsRepository

import com.elsharif.moviesapp.moviesList.data.local.movie.MovieDatabase
import com.elsharif.moviesapp.moviesList.data.local.movie.MovieEntity
import com.elsharif.moviesapp.moviesList.data.mappers.toMovie
import com.elsharif.moviesapp.moviesList.data.mappers.toMovieEntity
import com.elsharif.moviesapp.moviesList.data.remote.respnod.MovieDto
import com.elsharif.moviesapp.moviesList.domain.models.Movie
import com.elsharif.moviesapp.util.Constants
import com.elsharif.moviesapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExtraDetailsRepositoryImpl @Inject constructor(
    private val extraDetailsApi: ExtraDetailsApi,
    mediaDb: MovieDatabase
) : ExtraDetailsRepository {

    private val mediaDao = mediaDb.movieDao

    override suspend fun getSimilarMediaList(
        isRefresh: Boolean,
        type: String,
        id: Int,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Movie>>> {

        return flow {

            emit(Resource.Loading(true))

            val mediaEntity = mediaDao.getMediaById(id = id)

            val doesSimilarMediaListExist =
                (mediaEntity.similarMediaList != null && mediaEntity.similarMediaList != "-1,-2")

            if (!isRefresh && doesSimilarMediaListExist) {

                try {
                    val similarMediaListIds =
                        mediaEntity.similarMediaList?.split(",")!!.map { it.toInt() }

                    val similarMediaEntityList = ArrayList<MovieEntity>()
                    for (i in similarMediaListIds.indices) {
                        similarMediaEntityList.add(mediaDao.getMediaById(similarMediaListIds[i]))
                    }
                    emit(
                        Resource.Success(
                            data = similarMediaEntityList.map {
                                it.toMovie(
                                    type = it.mediaType ?: Constants.MOVIE,
                                    category = it.category ?: Constants.POPULAR
                                )
                            }
                        )
                    )
                } catch (e: Exception) {
                    emit(Resource.Error("Something went wrong."))
                }


                emit(Resource.Loading(false))
                return@flow


            }

            val remoteSimilarMediaList = fetchRemoteForSimilarMediaList(
                type = mediaEntity.mediaType ?: Constants.MOVIE,
                id = id,
                page = page,
                apiKey = apiKey
            )

            if (remoteSimilarMediaList == null) {
                emit(
                    Resource.Success(
                        data = emptyList()
                    )
                )
                emit(Resource.Loading(false))
                return@flow
            }

            remoteSimilarMediaList.let { similarMediaList ->

                val similarMediaListIntIds = ArrayList<Int>()
                for (i in similarMediaList.indices) {
                    similarMediaListIntIds.add(similarMediaList[i].id ?: -1)
                }

                mediaEntity.similarMediaList = try {
                    similarMediaListIntIds.joinToString(",")
                } catch (e: Exception) {
                    "-1,-2"
                }

                val similarMediaEntityList = remoteSimilarMediaList.map {
                    it.toMovieEntity(
                        type = it.media_type ?: Constants.MOVIE,
                        category = mediaEntity.category ?: Constants.POPULAR
                    )
                }

                mediaDao.insertMediaList(similarMediaEntityList)
                mediaDao.updateMediaItem(mediaEntity)

                emit(
                    Resource.Success(
                        data = similarMediaEntityList.map {
                            it.toMovie(
                                type = it.mediaType ?: Constants.MOVIE,
                                category = it.category ?: Constants.POPULAR
                            )
                        }
                    )
                )

                emit(Resource.Loading(false))

            }


        }

    }
    override suspend fun getRecommendationsMediaList(
        isRefresh: Boolean,
        type: String,
        id: Int,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Movie>>> {

        return flow {

            emit(Resource.Loading(true))

            val mediaEntity = mediaDao.getMediaById(id = id)

            val doesRecommendationsMediaListExist =
                (mediaEntity.recommendationsMediaList != null && mediaEntity.recommendationsMediaList != "-1,-2")

            if (!isRefresh && doesRecommendationsMediaListExist) {

                try {
                    val recommendationsMediaListIds =
                        mediaEntity.recommendationsMediaList?.split(",")!!.map { it.toInt() }

                    val recommendationsMediaEntityList = ArrayList<MovieEntity>()
                    for (i in recommendationsMediaListIds.indices) {
                        recommendationsMediaEntityList.add(mediaDao.getMediaById(recommendationsMediaListIds[i]))
                    }
                    emit(
                        Resource.Success(
                            data = recommendationsMediaEntityList.map {
                                it.toMovie(
                                    type = it.mediaType ?: Constants.MOVIE,
                                    category = it.category ?: Constants.POPULAR
                                )
                            }
                        )
                    )
                } catch (e: Exception) {
                    emit(Resource.Error("Something went wrong."))
                }


                emit(Resource.Loading(false))
                return@flow


            }

            val remoteRecommendationsMediaList = fetchRemoteForRecommendationsMediaList(
                type = mediaEntity.mediaType ?: Constants.MOVIE,
                id = id,
                page = page,
                apiKey = apiKey
            )

            if (remoteRecommendationsMediaList == null) {
                emit(
                    Resource.Success(
                        data = emptyList()
                    )
                )
                emit(Resource.Loading(false))
                return@flow
            }

            remoteRecommendationsMediaList.let { recommendationsMediaList ->

                val recommendationsMediaListIntIds = ArrayList<Int>()
                for (i in recommendationsMediaList.indices) {
                    recommendationsMediaListIntIds.add(recommendationsMediaList[i].id ?: -1)
                }

                mediaEntity.recommendationsMediaList = try {
                    recommendationsMediaListIntIds.joinToString(",")
                } catch (e: Exception) {
                    "-1,-2"
                }

                val recommendationsMediaEntityList = remoteRecommendationsMediaList.map {
                    it.toMovieEntity(
                        type = it.media_type ?: Constants.MOVIE,
                        category = mediaEntity.category ?: Constants.POPULAR
                    )
                }

                mediaDao.insertMediaList(recommendationsMediaEntityList)
                mediaDao.updateMediaItem(mediaEntity)

                emit(
                    Resource.Success(
                        data = recommendationsMediaEntityList.map {
                            it.toMovie(
                                type = it.mediaType ?: Constants.MOVIE,
                                category = it.category ?: Constants.POPULAR
                            )
                        }
                    )
                )

                emit(Resource.Loading(false))

            }


        }

    }

    private suspend fun fetchRemoteForSimilarMediaList(
        type: String,
        id: Int,
        page: Int,
        apiKey: String
    ): List<MovieDto>? {

        val remoteSimilarMediaList = try {
            extraDetailsApi.getSimilarMediaList(
                type = type,
                id = id,
                page = page,
                apiKey = apiKey
            )
        } catch (e: IOException) {
            e.printStackTrace()
            null
        } catch (e: HttpException) {
            e.printStackTrace()
            null
        }

        return remoteSimilarMediaList?.results

    }

    private suspend fun fetchRemoteForRecommendationsMediaList(
        type: String,
        id: Int,
        page: Int,
        apiKey: String
    ): List<MovieDto>? {

        val remoteRecommendationsMediaList = try {
            extraDetailsApi.getRecommendationsMediaList(
                type = type,
                id = id,
                page = page,
                apiKey = apiKey
            )
        } catch (e: IOException) {
            e.printStackTrace()
            null
        } catch (e: HttpException) {
            e.printStackTrace()
            null
        }

        return remoteRecommendationsMediaList?.results

    }


    override suspend fun getVideosList(
        isRefresh: Boolean,
        id: Int,
        apiKey: String
    ): Flow<Resource<List<String>>> {

        return flow {

            emit(Resource.Loading(true))

            val mediaEntity = mediaDao.getMediaById(id = id)

            val doVideosExist = (mediaEntity.videos != null)

            if (!isRefresh && doVideosExist) {

                try {
                    val videosIds =
                        mediaEntity.videos?.split(",")!!.map { it }

                    emit(
                        Resource.Success(data = videosIds)
                    )
                } catch (e: Exception) {
                    emit(Resource.Error("Something went wrong."))
                }

                emit(Resource.Loading(false))
                return@flow


            }

            val videosIds = fetchRemoteForVideosIds(
                type = mediaEntity.mediaType ?: Constants.MOVIE,
                id = id,
                apiKey = apiKey
            )

            if (videosIds == null) {
                emit(
                    Resource.Success(
                        data = emptyList()
                    )
                )
                emit(Resource.Loading(false))
                return@flow
            }

            videosIds.let {
                mediaEntity.videos = try {
                    it.joinToString(",")
                } catch (e: Exception) {
                    "-1,-2"
                }

                mediaDao.updateMediaItem(mediaEntity)

                emit(
                    Resource.Success(data = videosIds)
                )

                emit(Resource.Loading(false))

            }


        }
    }


    private suspend fun fetchRemoteForVideosIds(
        type: String,
        id: Int,
        apiKey: String
    ): List<String>? {

        val remoteVideosIds = try {
            extraDetailsApi.getVideosList(
                type = type,
                id = id,
                apiKey = apiKey
            )
        } catch (e: IOException) {
            e.printStackTrace()
            null
        } catch (e: HttpException) {
            e.printStackTrace()
            null
        }

        val listToReturn = remoteVideosIds?.results?.filter {
            it.site == "YouTube" && it.type == "Featurette" || it.type == "Teaser"
        }

        return listToReturn?.map {
            it.key
        }

    }



    // later
    override suspend fun getCastList(
        isRefresh: Boolean,
        id: Int,
        apiKey: String
    ): Flow<Resource<Cast>> {

        return flow {

//            emit(Resource.Loading(true))
//
//            val mediaEntity = mediaDao.getMediaById(id = id)
//
//            val doCastExist = !(mediaEntity.runtime == null ||
//                    mediaEntity.status == null || mediaEntity.tagline == null)
//
//            if (!isRefresh && doCastExist) {
//                emit(
//                    Resource.Success(
//                        data = mediaEntity.toMedia(
//                            type = mediaEntity.mediaType ?: Constants.MOVIE,
//                            category = mediaEntity.category ?: Constants.POPULAR
//                        )
//                    )
//                )
//
//                emit(Resource.Loading(false))
//                return@flow
//            }
//
//            val remoteDetails = fetchRemoteForDetails(
//                type = mediaEntity.mediaType ?: Constants.MOVIE,
//                id = id,
//                apiKey = apiKey
//            )
//
//            if (remoteDetails == null) {emit(
//                Resource.Success(
//                    data = mediaEntity.toMedia(
//                        type = mediaEntity.mediaType ?: Constants.MOVIE,
//                        category = mediaEntity.category ?: Constants.POPULAR
//                    )
//                )
//            )
//                emit(Resource.Loading(false))
//                return@flow
//            }
//
//            remoteDetails.let { details ->
//
//                mediaEntity.runtime = details.runtime
//                mediaEntity.status = details.status
//                mediaEntity.tagline = details.tagline
//
//                mediaDao.updateMediaItem(mediaEntity)
//
//                emit(
//                    Resource.Success(
//                        data = mediaEntity.toMedia(
//                            type = mediaEntity.mediaType ?: Constants.MOVIE,
//                            category = mediaEntity.category ?: Constants.POPULAR
//                        )
//                    )
//                )
//
//                emit(Resource.Loading(false))
//
//            }


        }

    }


}










