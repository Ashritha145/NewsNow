package com.example.newsnow.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsnow.data.model.Article
import com.example.newsnow.data.repository.NewsRepository
import com.example.newsnow.utlis.NetworkResponse
import kotlinx.coroutines.launch
import kotlin.collections.any
import kotlin.collections.removeAll


class NewsViewModel(): ViewModel() {
    private var repository: NewsRepository = NewsRepository()

    private val _news = MutableLiveData<NetworkResponse<List<Article>>>()
    val news: LiveData<NetworkResponse<List<Article>>> = _news


    fun loadTopHeadLines(country: String = "us", category: String = "general") {
        viewModelScope.launch {
            _news.value = NetworkResponse.Loading
            val response = repository.getTopHeadLines(category)
            _news.value = response
        }
    }

    fun searchResults(query: String) {
        viewModelScope.launch {
            _news.value = NetworkResponse.Loading
            val response = repository.searchNews(query)
            _news.value = response
        }

    }
}