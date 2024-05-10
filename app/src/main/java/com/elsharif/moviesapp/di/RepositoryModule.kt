package com.elsharif.moviesapp.di

import com.elsharif.moviesapp.media_details.data.repository.DetailsRepositoryImpl
import com.elsharif.moviesapp.media_details.data.repository.ExtraDetailsRepositoryImpl
import com.elsharif.moviesapp.media_details.domain.repository.DetailsRepository
import com.elsharif.moviesapp.media_details.domain.repository.ExtraDetailsRepository
import com.elsharif.moviesapp.moviesList.data.repository.GenreRepositoryImpl
import com.elsharif.moviesapp.moviesList.domain.repository.MovieListRepository
import com.elsharif.moviesapp.moviesList.data.repository.MovieListRepositoryImpl
import com.elsharif.moviesapp.moviesList.domain.repository.GenreRepository
import com.elsharif.moviesapp.search.data.repository.SearchRepositoryImpl
import com.elsharif.moviesapp.search.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
/*

    @Binds
    @Singleton
    abstract fun bindMovieListRepository(
        movieListRepositoryImpl: MovieListRepositoryImpl
    ): MovieListRepository
*/

    @Binds
    @Singleton
    abstract fun bindMediaRepository(
        mediaRepositoryImpl: MovieListRepositoryImpl
    ): MovieListRepository

    @Binds
    @Singleton
    abstract fun bindSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    @Singleton
    abstract fun bindGenreRepository(
        genreRepositoryImpl: GenreRepositoryImpl
    ): GenreRepository

    @Binds
    @Singleton
    abstract fun bindDetailsRepository(
        detailsRepositoryImpl: DetailsRepositoryImpl
    ): DetailsRepository

    @Binds
    @Singleton
    abstract fun bindExtraDetailsRepository(
        extraDetailsRepositoryImpl: ExtraDetailsRepositoryImpl
    ): ExtraDetailsRepository

}













