package com.example.newsnow

import  android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.newsnow.navigation.NavigationController
import com.example.newsnow.ui.viewmodel.AuthViewModel
import com.example.newsnow.ui.viewmodel.ChatBotViewModel
import com.example.newsnow.ui.viewmodel.NewsViewModel
import com.example.newsnow.ui.viewmodel.SavedArticleViewModel
import com.example.newsnow.ui.viewmodel.SearchViewModel
import com.example.newsnow.utlis.PreferenceManager
import kotlin.getValue

class MainActivity : ComponentActivity() {
    val authViewModel: AuthViewModel by viewModels()

    val chatBotViewModel: ChatBotViewModel by viewModels()

    val searchViewModel: SearchViewModel by viewModels()

    val savedArticleViewModel: SavedArticleViewModel by viewModels()
    val newsViewModel: NewsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Adjust status and navigation bar appearance
        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = true
        insetsController.isAppearanceLightNavigationBars = true

        // Optional: Set specific colors for status and navigation bars
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        window.navigationBarColor = android.graphics.Color.TRANSPARENT
        val preferenceManager= PreferenceManager(applicationContext,authViewModel)
        val onboardingCompleted=preferenceManager.isOnBoardingCompleted()

        val startDestination=if (onboardingCompleted) "login" else "onboarding"
        setContent {
            Surface(color = MaterialTheme.colorScheme.background) {
                NavigationController(authViewModel = authViewModel, newsViewModel = newsViewModel,chatBotViewModel=chatBotViewModel,searchViewModel=searchViewModel,startDestination=startDestination, preferenceManager =
                    preferenceManager,savedArticleViewModel=savedArticleViewModel)
            }
        }
    }
}