package ru.igorshaposhnikov.currents.data.repository.mappers

import ru.igorshaposhnikov.currents.data.local.BookmarkEntity
import ru.igorshaposhnikov.currents.data.repository.model.Article

fun BookmarkEntity.toArticle(): Article = Article(
    sourceName = sourceName,
    author = author,
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    content = content
)

fun Article.toBookmarkEntity(): BookmarkEntity = BookmarkEntity(
    url = url,
    sourceName = sourceName,
    author = author,
    title = title,
    description = description,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    content = content
)
