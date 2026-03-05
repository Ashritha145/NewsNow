package com.example.newsnow.data.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsnow.data.model.ArticleEntity
import retrofit2.http.DELETE

//TODO
//insert saved_article
//delete saved_article
//get saved articles
//is article saved..
@Dao
interface SavedArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: ArticleEntity)

    @Query("DELETE FROM saved_articles WHERE url = :url AND userId=:userId")
    suspend fun deleteArticle(url: String?,userId: String)

    @Query("SELECT * FROM saved_articles WHERE userId=:userId")
    suspend fun getSavedArticles(userId: String):List<ArticleEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM saved_articles WHERE url=:url AND userId=:userId)")
    suspend fun isArticleSaved(url:String?,userId: String): Boolean
}
