package com.example.newsnow.data.model

import androidx.annotation.DrawableRes
import com.example.newsnow.R

sealed class onBoardingModel(
    val appName1: String,
    val appName2:String,
    @DrawableRes val image:Int,
    val title:String,
    val description: String
){
    data object FirstPage: onBoardingModel(
        appName1="",
        appName2="",
        image = R.drawable.img_2,
        title = "Stay updated with NewsNow",
        description = "Get the latest headlines, personalized updates, and trending stories all in one place."
    )

    data object SecondPage: onBoardingModel(
        appName1="",
        appName2="",
        image=R.drawable.img_1,
        title = "Find What Matters",
        description = "Search for any topic or region easily, and explore the stories that interest you the most."
    )
    data object ThirdPage: onBoardingModel(
        appName1="News",
        appName2="Now",
        image = R.drawable.img,
        title = "AI News Assistant",
        description = "Ask questions, get summaries..."
    )
}