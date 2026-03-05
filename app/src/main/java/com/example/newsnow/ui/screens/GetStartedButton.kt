package com.example.newsnow.ui.screens


import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun GetStartedButton(onFinish:()-> Unit){
    Button(onClick = onFinish,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue
        ),
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp),

        )
    ) {
        Text(text = "Get Started", color = Color.White)
    }
}