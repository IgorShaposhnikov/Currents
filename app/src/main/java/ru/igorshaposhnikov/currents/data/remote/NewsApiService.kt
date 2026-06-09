package ru.igorshaposhnikov.currents.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import ru.igorshaposhnikov.currents.data.model.NewsResponse

interface NewsApiService {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("category") category: String? = null,
        @Query("q") query: String? = null,
        @Query("pageSize") pageSize: Int = 20,
        @Query("page") page: Int = 1
    ): NewsResponse
}
