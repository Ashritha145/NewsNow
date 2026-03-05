package com.example.newsnow.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlin.text.isNotBlank

class SearchViewModel: ViewModel() {
    private val _recentlySearched = mutableStateListOf<String>()
    val recentlySearched: List<String> = _recentlySearched

    fun addSearch(query:String) {
        if (query.isNotBlank()) {
            _recentlySearched.remove(query)
            _recentlySearched.add(0, query)
        }

    }
    fun removeSearch(query: String){
        _recentlySearched.remove(query)
    }
}