package com.example.newsnow.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsnow.data.model.Article
import com.example.newsnow.data.repository.SavedArticleRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class SavedArticleViewModel(application: Application): AndroidViewModel(application) {
    private val repository: SavedArticleRepository = SavedArticleRepository(application)

    private val _savedArticles = MutableLiveData<List<Article>>()
    val savedArticles: LiveData<List<Article>> = _savedArticles

    private val _isArticleSaved = MutableLiveData<Map<String,Boolean>>(emptyMap())
    val isArticleSaved: LiveData<Map<String,Boolean>> = _isArticleSaved

   private val auth= FirebaseAuth.getInstance()
    private fun getUserId(): String? = auth.currentUser?.uid

    fun loadSavedArticles(){
        val userId = getUserId() ?: return
        viewModelScope.launch {
           _savedArticles.value=repository.getSavedArticles(userId)
        }
    }

    fun isArticleSaved(url: String?) {
        if (url==null) return
        val userId = getUserId() ?: return
        viewModelScope.launch {
            val isSaved=repository.isArticleSaved(url,userId)
            _isArticleSaved.value = _isArticleSaved.value!!
                .toMutableMap()
                .apply {
                    put(url,isSaved)
                }
        }
    }

    fun toggleSavedArticle(article: Article){
        val userId = getUserId() ?: return
       viewModelScope.launch {
           val url=article.url?:return@launch
           val currentlySaved=repository.isArticleSaved(article.url,userId)
           if(currentlySaved){
               repository.deleteArticle(article,userId)
           }else{
              repository.saveArticle(article,userId)
           }
           loadSavedArticles()
           _isArticleSaved.value=_isArticleSaved.value!!.toMutableMap().apply {
               put(url,repository.isArticleSaved(url,userId))
           }
       }
    }
}