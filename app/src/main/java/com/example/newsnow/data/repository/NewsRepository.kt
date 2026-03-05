package com.example.newsnow.data.repository


import com.example.newsnow.data.model.Article
import com.example.newsnow.data.service.NewsApiService
import com.example.newsnow.utlis.Constants.Companion.API_KEY
import com.example.newsnow.utlis.Constants.Companion.BASE_URL
import com.example.newsnow.utlis.NetworkResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java


class NewsRepository() {

    private val newsApiService: NewsApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }

    suspend fun getTopHeadLines(category: String): NetworkResponse<List<Article>>{
      return try{
          val response=newsApiService.getTopHeadLines(apiKey=API_KEY,category )
          NetworkResponse.Success(response.articles)
      }catch (e: Exception){
          NetworkResponse.Error("Error in fetching the details ${e.message}")
      }
    }

    suspend fun searchNews(query: String): NetworkResponse<List<Article>>{
        return try {
            val response= newsApiService.searchNews(query,apiKey=API_KEY)
            NetworkResponse.Success(response.articles)
        }catch (e: Exception){
            NetworkResponse.Error("Error in searching News ${e.message}")
        }
    }
}

