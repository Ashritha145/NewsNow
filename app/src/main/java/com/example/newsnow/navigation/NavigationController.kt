package com.example.newsnow.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsnow.ui.screens.ChatbotScreen
import com.example.newsnow.ui.screens.HomeScreen
import com.example.newsnow.ui.screens.Login
import com.example.newsnow.ui.screens.MainScreen
import com.example.newsnow.ui.screens.ProfileScreen
import com.example.newsnow.ui.screens.SavedScreen
import com.example.newsnow.ui.screens.SearchScreen
import com.example.newsnow.ui.screens.onBoardingScreen
import com.example.newsnow.ui.screens.signUp
import com.example.newsnow.ui.viewmodel.AuthViewModel
import com.example.newsnow.ui.viewmodel.ChatBotViewModel
import com.example.newsnow.ui.viewmodel.NewsViewModel
import com.example.newsnow.ui.viewmodel.SavedArticleViewModel
import com.example.newsnow.ui.viewmodel.SearchViewModel
import com.example.newsnow.utlis.PreferenceManager


@Composable
fun NavigationController(modifier: Modifier= Modifier, authViewModel: AuthViewModel, newsViewModel: NewsViewModel
                         , chatBotViewModel: ChatBotViewModel, searchViewModel: SearchViewModel, startDestination: String,
                         preferenceManager: PreferenceManager,savedArticleViewModel: SavedArticleViewModel
) {
    val navController= rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination, builder = {
            composable("onboarding") {
                onBoardingScreen(onFinish = {
                    preferenceManager.setOnBoardingCompleted(true)
                    navController.navigate("login"){
                        popUpTo("onboarding"){inclusive=true}
                    }
                })

            }
            composable("login") { Login(modifier,navController,authViewModel,newsViewModel) }
            composable("signup") { signUp(modifier,navController,authViewModel) }
            composable("main") { MainScreen(navController) }
            composable("Home") { HomeScreen(navController,newsViewModel,savedArticleViewModel)}
            composable("Search") { SearchScreen(navController,searchViewModel,newsViewModel,savedArticleViewModel,preferenceManager) }
            composable("Saved") { SavedScreen(navController,savedArticleViewModel) }
            composable("ChatBot") { ChatbotScreen(navController,chatBotViewModel) }
            composable("profile") { ProfileScreen(authViewModel,navController,newsViewModel) }
        }
    )
}