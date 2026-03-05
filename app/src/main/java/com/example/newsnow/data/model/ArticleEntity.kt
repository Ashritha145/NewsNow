package com.example.newsnow.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("saved_articles",
    primaryKeys = ["url","userId"]
    )
data class ArticleEntity(
    val url:String,
    val userId: String,
    val author:String?,
    val title:String?,
    val description:String?,
    val urlToImage:String?,

    )