package com.example.newsnow.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import kotlin.collections.forEachIndexed


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController){

    val navItemList= listOf(
        NavItem("Home", R.drawable.icons8_home),
        NavItem("Search", R.drawable.search_image),
        NavItem("Saved", R.drawable.vector__2_),
        NavItem("ChatBot", R.drawable.vector__3_)

    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.padding(top = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                            Text(text = "News",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 32.sp
                            )
                            Text(text = "Now",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 32.sp,
                                color = Color.Blue
                            )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Account",
                            modifier=Modifier
                                .padding(end = 12.dp)
                                .size(40.dp)
                                .clickable {

                                }
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                navItemList.forEachIndexed { index, item->
                    NavigationBarItem(
                        selected = currentRoute == item.label,
                                onClick = {
                                    navController.navigate(item.label) {
                                        popUpTo("Home") { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }

                                },
                        icon = { Icon(painter = painterResource(id=item.icon), contentDescription = item.label) },
                        label = { Text(text = item.label)},
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
    ){paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {  }
    }


}