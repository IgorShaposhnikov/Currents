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

enum class NewsTab { ALL, BOOKMARKS }

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NewsRepository = NewsRepositoryImpl(
        database = AppDatabase.getInstance(application)
    )

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _selectedTab = MutableStateFlow(NewsTab.ALL)
    val selectedTab: StateFlow<NewsTab> = _selectedTab.asStateFlow()

    private var newsCache: List<Article> = emptyList()

    init {
        fetchNews()
    }

    fun selectTab(tab: NewsTab) {
        _selectedTab.value = tab
        when (tab) {
            NewsTab.ALL -> {
                if (newsCache.isNotEmpty()) {
                    _uiState.value = UiState.Success(newsCache)
                } else {
                    fetchNews()
                }
            }
            NewsTab.BOOKMARKS -> loadBookmarks()
        }
    }

    fun refresh() {
        if (_isRefreshing.value) return
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                val articles = when (_selectedTab.value) {
                    NewsTab.ALL -> {
                        repository.getTopHeadlines().also { newsCache = it }
                    }
                    NewsTab.BOOKMARKS -> repository.getAllBookmarks()
                }
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
            if (_selectedTab.value == NewsTab.BOOKMARKS) {
                loadBookmarks()
            }
        }
    }

    suspend fun isBookmarked(url: String): Boolean = repository.isBookmarked(url)

    private fun loadBookmarks() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val articles = repository.getAllBookmarks()
                _uiState.value = UiState.Success(articles)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Something went wrong")
            }
        }
    }

    private fun fetchNews() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val articles = repository.getTopHeadlines()
                newsCache = articles
                _uiState.value = UiState.Success(articles)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Something went wrong")
            }
        }
    }
}
