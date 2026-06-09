package ru.igorshaposhnikov.currents.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.igorshaposhnikov.currents.data.local.AppDatabase
import ru.igorshaposhnikov.currents.data.repository.NewsRepository
import ru.igorshaposhnikov.currents.data.repository.NewsRepositoryImpl
import ru.igorshaposhnikov.currents.data.repository.model.Article

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NewsRepository = NewsRepositoryImpl(
        database = AppDatabase.getInstance(application)
    )

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        fetchNews()
    }

    fun refresh() {
        if (_isRefreshing.value) return
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                val articles = repository.getTopHeadlines()
                _uiState.value = UiState.Success(articles)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Something went wrong")
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    fun toggleBookmark(article: Article) {
        viewModelScope.launch {
            repository.toggleBookmark(article)
        }
    }

    suspend fun isBookmarked(url: String): Boolean = repository.isBookmarked(url)

    private fun fetchNews() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val articles = repository.getTopHeadlines()
                _uiState.value = UiState.Success(articles)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Something went wrong")
            }
        }
    }
}
