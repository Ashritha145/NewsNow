package com.example.newsnow.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun pagerIndicator(
    pageSize:Int,
    currentPage:Int,
    selectedColor: Color = MaterialTheme.colorScheme.secondary,
    unselectedColor: Color=MaterialTheme.colorScheme.secondaryContainer
){
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.background(Color.White))
    {
        repeat(pageSize) {
            Spacer(modifier = Modifier.size(2.5.dp))
            Box(
                modifier = Modifier
                    .height(14.dp)
                    .width(width = if (it == currentPage) 32.dp else 16.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(if (it == currentPage) selectedColor else unselectedColor)

            )
            Spacer(modifier = Modifier.size(2.5.dp))
        }

    }
}

@Preview(showBackground = true)
@Composable
fun IndicatorUi1(){
    pagerIndicator(3,0)
}
@Preview(showBackground = true)
@Composable
fun IndicatorUi2(){
    pagerIndicator(3,1)
}
@Preview(showBackground = true)
@Composable
fun IndicatorUi3(){
    pagerIndicator(3,2)
}