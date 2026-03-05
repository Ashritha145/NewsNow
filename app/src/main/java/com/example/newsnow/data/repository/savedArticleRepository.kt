package com.example.newsnow.data.repository

import android.content.Context
import com.example.newsnow.data.model.Article
import com.example.newsnow.data.model.ArticleEntity
import com.example.newsnow.data.service.SavedArticleDatabase

class SavedArticleRepository(context: Context) {

    private val dao = SavedArticleDatabase.getDatabase(context).getArticleDao()

    suspend fun saveArticle(article: Article,userId: String){
        val safeUrl=article.url?:return
        dao.insertArticle(
            ArticleEntity(
                url = safeUrl,
                userId=userId,
                article.author,
                article.title,
                article.description,
                article.urlToImage,
            )
        )
    }

    suspend fun deleteArticle(article: Article, userId:String){
        article.url?.let {  dao.deleteArticle(article.url,userId)}
    }

    suspend fun getSavedArticles(userId: String):List<Article>{
        return dao.getSavedArticles(userId).map{
            Article(
                author = it.author,
                title = it.title,
                description = it.description,
                url=it.url,
                urlToImage = it.urlToImage
            )
        }
    }

    suspend fun isArticleSaved(url: String?, userId: String): Boolean{
        if(url==null) return false
        return dao.isArticleSaved(url = url,userId)
    }

}