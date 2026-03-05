package com.example.newsnow.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.newsnow.data.model.Article
import com.example.newsnow.ui.viewmodel.NewsViewModel
import com.example.newsnow.ui.viewmodel.SavedArticleViewModel


@Composable
fun NewsList(articles: List<Article>, viewModel: SavedArticleViewModel){
    LazyColumn {
        items(articles){article->
            NewsItem(article,viewModel)
        }
    }
}

