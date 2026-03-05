package com.example.newsnow.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsnow.data.model.onBoardingModel

@Composable
fun pagerScreen(onBoardingModel: onBoardingModel, currentPage:Int) {

    Column(
        modifier=Modifier
            .padding(33.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        Row(
            modifier = if (currentPage==2) Modifier.padding(bottom = 130.dp) else Modifier.padding(0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = onBoardingModel.appName1,
                fontWeight = FontWeight.SemiBold,
                fontSize = 32.sp
            )
            Text(
                text = onBoardingModel.appName2,
                fontWeight = FontWeight.SemiBold,
                fontSize = 32.sp,
                color = Color.Blue
            )
        }

        Image(painter = painterResource(id = onBoardingModel.image), contentDescription = null,
            modifier = Modifier.size(180.dp)
        )
        Text(text = onBoardingModel.title, fontSize = 32.sp, fontWeight = FontWeight.W600, textAlign = TextAlign.Center)
        Spacer(modifier=Modifier.height(33.dp))
        Text(text = onBoardingModel.description, fontSize = 18.sp, fontWeight = FontWeight.W600,color=Color(0xFF454545), textAlign = TextAlign.Center)
    }


}
