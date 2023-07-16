package com.arifwidayana.musiclens.presentation.ui

sealed class MainEvent {
    data class SearchQueryChange(val query: String): MainEvent()
}