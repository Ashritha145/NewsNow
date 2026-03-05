package com.example.newsnow.data.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsnow.data.model.Article
import com.example.newsnow.data.model.ArticleEntity


@Database(
    entities = [ArticleEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SavedArticleDatabase: RoomDatabase() { // extends room database
    abstract fun getArticleDao(): SavedArticleDao
    companion object{
        var INSTANCE: SavedArticleDatabase?=null

        fun getDatabase(context: Context): SavedArticleDatabase{
            return INSTANCE?:synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    SavedArticleDatabase::class.java,
                    "article_database"
                ).build()
                INSTANCE=instance
                instance
            }
        }
    }
}