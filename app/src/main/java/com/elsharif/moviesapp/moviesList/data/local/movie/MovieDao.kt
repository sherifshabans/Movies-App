package com.elsharif.moviesapp.moviesList.data.local.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMediaList(
        mediaEntities: List<MovieEntity>
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMediaItem(
        mediaItem: MovieEntity
    )

    @Update
    suspend fun updateMediaItem(
        mediaItem: MovieEntity
    )

    @Query(
        """
            DELETE FROM movieentity
            WHERE mediaType = :mediaType AND category = :category
        """
    )
    suspend fun deleteMediaByTypeAndCategory(mediaType: String, category: String)

    @Query("SELECT * FROM movieentity WHERE id = :id")
    suspend fun getMediaById(id: Int): MovieEntity

    @Query(
        """
            SELECT * 
            FROM movieentity 
            WHERE mediaType = :mediaType AND category = :category
        """
    )
    suspend fun getMediaListByTypeAndCategory(
        mediaType: String, category: String
    ): List<MovieEntity>

    @Query(
        """
            DELETE FROM movieentity 
            WHERE category = :category
        """
    )
    suspend fun deleteTrendingMediaList(category: String)


    @Query(
        """
            SELECT * 
            FROM movieentity 
            WHERE category = :category
        """
    )
    suspend fun getTrendingMediaList(category: String): List<MovieEntity>

}














