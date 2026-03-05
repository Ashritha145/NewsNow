package com.example.newsnow.utlis

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.newsnow.ui.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import androidx.core.content.edit

class PreferenceManager(context: Context,viewModel: AuthViewModel){
    private val sharedPreferences=context.getSharedPreferences("MyPrefs",Context.MODE_PRIVATE)

    var username by mutableStateOf(sharedPreferences.getString("username",viewModel.getUserName()))
    //saves a boolean value to to remember if onBoarding is completed

    private val prefs=context.getSharedPreferences("searchPrefs", Context.MODE_PRIVATE)


    companion object{

        private val auth= FirebaseAuth.getInstance()

        fun fetchUserId(): String?{
            return auth.currentUser?.uid
        }
        val userId=fetchUserId()
        var KEY_RECENT_SEARCHES="recent_searches_$userId"
        private const val MAX_RECENT_SEARCHES=10
    }
    fun setOnBoardingCompleted(completed: Boolean){
        sharedPreferences.edit {
            putBoolean("onboarding_completed", completed)
        }
    }

    //checks if onBoarding completed before
    fun isOnBoardingCompleted(): Boolean{
        return sharedPreferences.getBoolean("onboarding_completed",false)
    }

    fun saveUserName(newName:String){
        username=newName
        sharedPreferences.edit { putString("username", newName) }
    }

    fun saveRecentSearch(query:String){
        val searches=prefs.getStringSet(KEY_RECENT_SEARCHES,mutableSetOf())?.toMutableList()?:mutableListOf()
        searches.remove(query)
        searches.add(0,query)

        if(searches.size > MAX_RECENT_SEARCHES){
            searches.subList(MAX_RECENT_SEARCHES,searches.size).clear()
        }
        prefs.edit {
            putStringSet(KEY_RECENT_SEARCHES, searches.toSet())
        }
    }

    fun getRecentSearches():List<String>{
        return prefs.getStringSet(KEY_RECENT_SEARCHES,emptySet())?.toList()?:emptyList()
    }
    fun removeSearch(query:String){
        val searches=prefs.getStringSet(KEY_RECENT_SEARCHES,mutableSetOf())?.toMutableList()?:mutableListOf()
        searches.remove(query)
        prefs.edit{
            putStringSet(KEY_RECENT_SEARCHES,searches.toSet())
        }
    }
}
