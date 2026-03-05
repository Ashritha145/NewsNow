package com.example.newsnow.data.model

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles:List<Article>
)
