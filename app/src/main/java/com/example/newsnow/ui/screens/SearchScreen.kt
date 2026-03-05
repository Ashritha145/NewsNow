package com.example.newsnow.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.room.util.query
import com.example.newsnow.R
import com.example.newsnow.data.model.NavItem
import com.example.newsnow.ui.viewmodel.NewsViewModel
import com.example.newsnow.ui.viewmodel.SavedArticleViewModel
import com.example.newsnow.ui.viewmodel.SearchViewModel
import com.example.newsnow.utlis.NetworkResponse
import com.example.newsnow.utlis.PreferenceManager
import kotlin.collections.forEach
import kotlin.collections.forEachIndexed
import kotlin.collections.isNotEmpty
import kotlin.text.isNotEmpty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController, searchViewModel: SearchViewModel, newsViewModel: NewsViewModel,savedArticleViewModel: SavedArticleViewModel,preferenceManager: PreferenceManager) {
    val navItemList = listOf(
        NavItem("Home", R.drawable.icons8_home),
        NavItem("Search", R.drawable.search_image),
        NavItem("Saved", R.drawable.vector__2_),
        NavItem("ChatBot", R.drawable.vector__3_)

    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var searchQuery by remember { mutableStateOf("") }

    var showResults by rememberSaveable { mutableStateOf(false) }

    var recentlySearched by remember { mutableStateOf(preferenceManager.getRecentSearches()) }

    LaunchedEffect(Unit) {
        preferenceManager.getRecentSearches()
    }

    val news = newsViewModel.news.observeAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.padding(top = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "News",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 32.sp
                        )
                        Text(
                            text = "Now",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 32.sp,
                            color = Color.Blue
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Account",
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .size(40.dp)
                                .clickable {
                                    navController.navigate("profile")
                                },
                            tint = Color.Blue
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                navItemList.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = currentRoute == item.label,
                        onClick = {
                            navController.navigate(item.label) {
                                popUpTo("Home") { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }

                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.label
                            )
                        },
                        label = { Text(text = item.label) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF015DFC),
                            selectedTextColor = Color(0xFF015DFC),
                            unselectedIconColor = Color.Black,
                            unselectedTextColor = Color.Black
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(26.dp),
                trailingIcon = {
                    IconButton(onClick = {
                        if (searchQuery.isNotEmpty()){
                            preferenceManager.saveRecentSearch(searchQuery)
                            recentlySearched = preferenceManager.getRecentSearches()
                            newsViewModel.searchResults(searchQuery)
                            searchQuery = ""
                            showResults=true
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search",
                        ) }
                },
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                placeholder = {Text(text = "Search for Latest news",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray
                    )}

            )
if (showResults) {
    when (val state=news.value) {
        is NetworkResponse.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is NetworkResponse.Success -> {
            NewsList(state.data,savedArticleViewModel)
        }
        is NetworkResponse.Error -> {
            Text(
                text = "Error loading news",
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        }
        else -> Unit
    }
} else {
    if (recentlySearched.isNotEmpty()) {
        Text(
            text = "Recently Searched",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 26.dp, top = 16.dp, bottom = 8.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 26.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .padding(8.dp)

        ) {
            recentlySearched.forEach { search ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            newsViewModel.searchResults(search)
                            showResults = true
                        }
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,

                ) {
                    Text(
                        text = search,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    IconButton(onClick = {
                        preferenceManager.removeSearch(search)
                        recentlySearched=preferenceManager.getRecentSearches()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Delete"
                        )
                    }
                }
            }
        }
    }
    if (recentlySearched.isEmpty()){
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
            ) {
            Image(painter = painterResource(id = R.drawable.img_1),
                contentDescription = "SearchImage",
                modifier = Modifier .height(300.dp)
                    .width(405.dp)
            )
            Text(text = "Hello there! What’s on your mind today?", fontWeight = FontWeight.W600, fontSize = 13.sp)
        }
    }
                        }
                    }
                }
}

