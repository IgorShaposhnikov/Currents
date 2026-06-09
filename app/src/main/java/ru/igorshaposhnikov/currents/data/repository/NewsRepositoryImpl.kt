package ru.igorshaposhnikov.currents.data.repository

import ru.igorshaposhnikov.currents.data.remote.RetrofitInstance
import ru.igorshaposhnikov.currents.data.repository.model.Article
import ru.igorshaposhnikov.currents.data.repository.mappers.toArticle

class NewsRepositoryImpl : NewsRepository {

    private val api = RetrofitInstance.api

    override suspend fun getTopHeadlines(
        country: String,
        category: String?,
        query: String?,
        pageSize: Int,
        page: Int
    ): List<Article> {
        val response = api.getTopHeadlines(country, category, query, pageSize, page)
        return response.articles.map { it.toArticle() }
    }
}
