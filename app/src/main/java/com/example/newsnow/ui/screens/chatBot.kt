package com.example.newsnow.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.newsnow.R
import com.example.newsnow.data.model.NavItem
import com.example.newsnow.data.model.messageModel
import com.example.newsnow.ui.viewmodel.ChatBotViewModel
import kotlin.collections.forEachIndexed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatbotScreen(navController: NavController,viewModel: ChatBotViewModel) {
    val navItemList = listOf(
        NavItem("Home", R.drawable.icons8_home),
        NavItem("Search", R.drawable.search_image),
        NavItem("Saved", R.drawable.vector__2_),
        NavItem("ChatBot", R.drawable.vector__3_)

    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val messageList = viewModel.messageList
    var message by remember { mutableStateOf("") }
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
        Column(
            modifier = Modifier.padding(paddingValues)
                .fillMaxSize()
        ) {
            if (messageList.isEmpty()){
                Box(modifier= Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                    ) {
                    Column(
                        modifier = Modifier.align(Alignment.Center)
                            .padding(30.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "How can I help you with news today?",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 25.sp,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Card(shape = RoundedCornerShape(10.dp)) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.padding(5.dp)
                            ) {
                                Text(
                                    text = "Your AI news assistant is listening",
                                    color = Color.Gray,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }

            }
            else if (messageList.isNotEmpty()){
                LazyColumn(modifier= Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(8.dp),
                    reverseLayout = true

                ){
                    items(messageList.reversed()){
                        MessageRow(it)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = message,
                    onValueChange = { message = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    placeholder = { Text("Match highlights today", fontSize = 12.sp) },
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                IconButton(
                    onClick = {
                        viewModel.sendMessage(message)
                        message = ""
                    },
                    enabled = (message.isNotEmpty()),
                    modifier = Modifier
                        .background(if (message.isNotEmpty()) Color.Blue else Color.Gray, CircleShape)
                        .size(48.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.hugeicons_sent_02),
                        contentDescription = "send",
                        tint = if (message.isNotEmpty()) Color.White else Color.LightGray
                    )
                }
            }
        }

    }
}
@Composable
fun MessageRow(messageModel: messageModel){
    val isModel=messageModel.role=="model"

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier=Modifier
                    .align(if (isModel) Alignment.BottomStart else Alignment.BottomEnd)
                    .padding(
                        start=if(isModel) 8.dp else 70.dp,
                        end=if(isModel) 70.dp else 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .clip(RoundedCornerShape(
                        topStart = if (isModel) 0.dp else 15.dp,
                        topEnd = if (isModel) 15.dp else 0.dp,
                        bottomStart = 15.dp,
                        bottomEnd = 15.dp
                    ))
                    .background(if(isModel) Color.White else Color.Gray)
                    .padding(16.dp)

            ) {

                Text(text = messageModel.message,
                    fontWeight = FontWeight.W500,
                    color=Color.Black
                    )
            }
        }
    }
}
