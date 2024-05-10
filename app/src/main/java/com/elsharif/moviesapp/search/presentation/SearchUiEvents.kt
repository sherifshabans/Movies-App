package com.elsharif.moviesapp.search.presentation

import com.elsharif.moviesapp.moviesList.domain.models.Movie

sealed class SearchUiEvents {
    data class Refresh(val type: String) : SearchUiEvents()
    data class OnPaginate(val type: String) : SearchUiEvents()
    data class OnSearchQueryChanged(val query: String) : SearchUiEvents()
    data class OnSearchedItemClick(val media: Movie) : SearchUiEvents()
}