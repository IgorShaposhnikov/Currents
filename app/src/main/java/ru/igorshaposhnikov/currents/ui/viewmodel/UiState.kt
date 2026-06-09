package ru.igorshaposhnikov.currents.ui.viewmodel

import ru.igorshaposhnikov.currents.data.repository.model.Article

sealed interface UiState {
    data object Loading : UiState
    data class Success(val articles: List<Article>) : UiState
    data class Error(val message: String) : UiState
}
