package ru.igorshaposhnikov.currents.data.repository

import ru.igorshaposhnikov.currents.data.local.AppDatabase
import ru.igorshaposhnikov.currents.data.remote.RetrofitInstance
import ru.igorshaposhnikov.currents.data.repository.model.Article
import ru.igorshaposhnikov.currents.data.repository.mappers.toArticle
import ru.igorshaposhnikov.currents.data.repository.mappers.toBookmarkEntity

class NewsRepositoryImpl(
    private val database: AppDatabase,
) : NewsRepository {

    private val api = RetrofitInstance.api
    private val bookmarkDao = database.bookmarkDao()

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

    override suspend fun toggleBookmark(article: Article) {
        if (bookmarkDao.isBookmarked(article.url)) {
            bookmarkDao.delete(article.toBookmarkEntity())
        } else {
            bookmarkDao.insert(article.toBookmarkEntity())
        }
    }

    override suspend fun isBookmarked(url: String): Boolean {
        return bookmarkDao.isBookmarked(url)
    }

    override suspend fun getAllBookmarks(): List<Article> {
        return bookmarkDao.getAllBookmarks().map { it.toArticle() }
    }
}
