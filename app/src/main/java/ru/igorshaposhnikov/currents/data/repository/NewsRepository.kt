package ru.igorshaposhnikov.currents.data.repository

import ru.igorshaposhnikov.currents.data.repository.model.Article

interface NewsRepository {
    suspend fun getTopHeadlines(
        country: String = "us",
        category: String? = null,
        query: String? = null,
        pageSize: Int = 20,
        page: Int = 1
    ): List<Article>
}
