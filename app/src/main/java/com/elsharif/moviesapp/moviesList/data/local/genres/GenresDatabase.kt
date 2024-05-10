package com.elsharif.moviesapp.moviesList.data.local.genres

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elsharif.moviesapp.moviesList.data.local.genres.GenreDao
import com.elsharif.moviesapp.moviesList.data.local.genres.GenreEntity

@Database(
    entities = [GenreEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GenresDatabase: RoomDatabase() {
    abstract val genreDao: GenreDao
}