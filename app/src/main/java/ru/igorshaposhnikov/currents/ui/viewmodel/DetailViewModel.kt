package ru.igorshaposhnikov.currents.ui.viewmodel

import android.app.Application
import java.net.URLDecoder
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.igorshaposhnikov.currents.data.local.AppDatabase
import ru.igorshaposhnikov.currents.data.repository.NewsRepository
import ru.igorshaposhnikov.currents.data.repository.NewsRepositoryImpl
import ru.igorshaposhnikov.currents.data.repository.model.Article

class DetailViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle,
) : AndroidViewModel(application) {

    val articleUrl: String = checkNotNull(savedStateHandle["url"])
    val title: String = URLDecoder.decode(savedStateHandle["title"] ?: "", "UTF-8")
    val sourceName: String = URLDecoder.decode(savedStateHandle["source"] ?: "", "UTF-8")
    val description: String = URLDecoder.decode(savedStateHandle["desc"] ?: "", "UTF-8")

    private val repository: NewsRepository = NewsRepositoryImpl(
        database = AppDatabase.getInstance(application)
    )

    private val _isBookmarked = MutableStateFlow(false)
    val isBookmarked: StateFlow<Boolean> = _isBookmarked.asStateFlow()

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    init {
        viewModelScope.launch {
            _isBookmarked.value = repository.isBookmarked(articleUrl)
        }
    }

    fun toggleBookmark() {
        viewModelScope.launch {
            val article = Article(
                sourceName = sourceName,
                author = null,
                title = title,
                description = description,
                url = articleUrl,
                urlToImage = null,
                publishedAt = "",
                content = description,
            )
            repository.toggleBookmark(article)
            _isBookmarked.value = repository.isBookmarked(articleUrl)
        }
    }

    fun setSpeaking(speaking: Boolean) {
        _isSpeaking.value = speaking
    }
}
