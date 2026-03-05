package com.example.newsnow.ui.screens



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.newsnow.data.model.onBoardingModel
import kotlin.collections.lastIndex

@Composable
fun onBoardingScreen(onFinish:()-> Unit){
    val pages:List<onBoardingModel> =
        listOf(onBoardingModel.FirstPage, onBoardingModel.SecondPage, onBoardingModel.ThirdPage)

    val pagerState= rememberPagerState(initialPage = 0, pageCount = {pages.size})

    val currentPage= remember{ derivedStateOf { pagerState.currentPage } }
    Scaffold(bottomBar = {
        Row(modifier = Modifier.fillMaxWidth()
            .padding(bottom = 20.dp)
            .background(Color.White)
            ,
            horizontalArrangement = Arrangement.Center

            ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                pagerIndicator(pageSize = pages.size, currentPage = pagerState.currentPage)
                if (currentPage.value==pages.lastIndex){
                    GetStartedButton { onFinish() }
                }

            }


        }

    }) {paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxWidth().background(Color.White),
           contentAlignment = Alignment.Center
            ) {
            HorizontalPager(state = pagerState) { index->
                pagerScreen(onBoardingModel = pages[index], currentPage = pagerState.currentPage)
            }

        }

    }
    }
