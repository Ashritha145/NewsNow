package com.example.newsnow.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.newsnow.R
import com.example.newsnow.data.model.Article
import com.example.newsnow.ui.viewmodel.SavedArticleViewModel
import kotlin.collections.joinToString
import kotlin.collections.take
import kotlin.text.split


@Composable
fun NewsItem(article: Article, viewModel: SavedArticleViewModel){
    val context = LocalContext.current
    val savedMap by viewModel.isArticleSaved.observeAsState(emptyMap())
    val isSaved=savedMap[article.url]?:false
    LaunchedEffect(article.url) {
        viewModel.isArticleSaved(article.url)
    }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(9.dp),
            horizontalAlignment = Alignment.Start
        ) {
            AsyncImage(model = article.urlToImage?:"",
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .height(200.dp),
                contentScale = ContentScale.Crop,
                contentDescription = article.title
                )
            Spacer(modifier = Modifier.height(20.dp))

            Text(text = article.title?:"",style = MaterialTheme.typography.titleMedium,fontSize = 20.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                )
            Text(text = article.description?:"", color = Color.Gray, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            Row(       modifier =  Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
                ) {
                Row(modifier = Modifier
                        .clickable {
                            val url=article.url
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            context.startActivity(intent)
                        }) {
                    val author="By ${article.author}"
                    Text(text = author.limitWords(3),fontWeight = FontWeight.SemiBold,fontSize = 10.sp)
                    Spacer(modifier= Modifier.width(6.dp))
                    Text(text = "Readmore",
                        color = Color.Blue,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                        )

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Arrow",
                        tint = Color.Blue,
                        modifier = Modifier.size(14.dp)
                    )
                }
                IconButton(onClick = {
                   viewModel.toggleSavedArticle(article)
                    if (isSaved){
                        Toast.makeText(context,"Your Article is removed Successfully.",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context,"Your Article is Saved Successfully.",Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.stash_save_ribbon),
                        contentDescription = "Save",
                        tint = if (isSaved) Color.Black else Color.Unspecified
                    )
                }

            }
        }
    }
}
fun String.limitWords(maxWords: Int): String {
    val words = this.split(" ")
    return if (words.size <= maxWords) {
        this
    } else {
        words.take(maxWords).joinToString(" ") + "..."
    }
}
