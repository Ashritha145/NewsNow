package com.example.newsnow.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.newsnow.R
import com.example.newsnow.data.model.NavItem
import com.example.newsnow.ui.viewmodel.NewsViewModel
import com.example.newsnow.ui.viewmodel.SavedArticleViewModel
import com.example.newsnow.utlis.NetworkResponse
import kotlin.collections.forEachIndexed
import kotlin.text.replaceFirstChar
import kotlin.text.uppercase


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController,viewModel: NewsViewModel,savedViewModel: SavedArticleViewModel) {

    val navItemList = listOf(
        NavItem("Home", R.drawable.icons8_home),
        NavItem("Search", R.drawable.search_image),
        NavItem("Saved", R.drawable.vector__2_),
        NavItem("ChatBot", R.drawable.vector__3_)

    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val newsState=viewModel.news.observeAsState()

    var seletedCategory by remember { mutableStateOf("general") }
    val categories =
        listOf("general", "health", "entertainment", "business", "science", "sports", "technology")
    LaunchedEffect(seletedCategory) {
        viewModel.loadTopHeadLines(category = seletedCategory)
    }
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
            LazyRow {
                items(categories) { item ->
                    TextButton(onClick = {seletedCategory=item})
                     {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = item.replaceFirstChar {it.uppercase()},
                                color=if (seletedCategory==item) Color(0xFF015DFC) else Color.Black,
                                fontWeight = if (seletedCategory==item) FontWeight.Bold else FontWeight.Normal
                            )
                            if (seletedCategory==item){
                                Box(
                                  modifier = Modifier
                                      .height(2.dp)
                                      .width(30.dp)
                                      .background(Color(0xFF015DFC))
                                )
                            }
                        }
                    }

                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            when(val state=newsState.value){
                is NetworkResponse.Loading->{
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }


                }
                is NetworkResponse.Success ->{
                    NewsList(articles = state.data,savedViewModel)
                }
                is NetworkResponse.Error->{
                    Text(
                        text = state.message?:"Unknown Error",
                        modifier = Modifier.align(Alignment.Center as Alignment.Horizontal),
                        color=Color.Red
                    )
                }
                else->{}
            }

        }

    }
}

