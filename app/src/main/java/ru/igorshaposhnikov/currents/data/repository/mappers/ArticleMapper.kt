package ru.igorshaposhnikov.currents.data.repository.mappers

import ru.igorshaposhnikov.currents.data.model.ArticleDto
import ru.igorshaposhnikov.currents.data.repository.model.Article
import ru.igorshaposhnikov.currents.util.formatDate

fun ArticleDto.toArticle(): Article = Article(
    sourceName = source.name,
    author = author,
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
    publishedAt = formatDate(publishedAt),
    content = content
)
