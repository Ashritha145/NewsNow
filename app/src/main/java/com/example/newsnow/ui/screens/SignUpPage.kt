package com.example.newsnow.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.newsnow.R
import com.example.newsnow.ui.viewmodel.AuthState
import com.example.newsnow.ui.viewmodel.AuthViewModel

@Composable
fun signUp(modifier: Modifier= Modifier,navController: NavController,authViewModel: AuthViewModel){
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var name by remember {
        mutableStateOf("")
    }

    val authState=authViewModel.authState.observeAsState()

    val context= LocalContext.current
    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenticated-> navController.navigate("home"){
                popUpTo("signup"){inclusive=true}
            }
            is AuthState.Error-> Toast.makeText(context,
                (authState.value as AuthState.Error).message,Toast.LENGTH_SHORT).show()
            else->{}
        }
    }
    Column(
        modifier=modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.logo), contentDescription = "logo")
        Text(text = "Create Account", fontSize = 42.sp, color = Color(0xFF007BFD), fontWeight = FontWeight.W700)
        Spacer(modifier= Modifier.height(16.dp))
        OutlinedTextField(value = name, onValueChange = {name=it },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Name",
                    tint = Color(0xFF007BFD)
                )
            },
            colors= OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF007BFD),
                unfocusedBorderColor = Color(0xFF007BFD),
                focusedLabelColor = Color(0xFF007BFD),
                unfocusedLabelColor = Color(0xFF007BFD),
                cursorColor = Color(0xFF007BFD)
            ),
            label= { Text(text = "Name", color = Color(0xFF007BFD), fontWeight = FontWeight.W600) },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(56.dp)
        )
        Spacer(modifier= Modifier.height(16.dp))
        OutlinedTextField(value = email, onValueChange = {email=it },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "email",
                    tint = Color(0xFF007BFD)
                )
            },
            colors= OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF007BFD),
                unfocusedBorderColor = Color(0xFF007BFD),
                focusedLabelColor = Color(0xFF007BFD),
                unfocusedLabelColor = Color(0xFF007BFD),
                cursorColor = Color(0xFF007BFD)
            ),
            label= { Text(text = "Email", color = Color(0xFF007BFD), fontWeight = FontWeight.W600) },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(56.dp)
        )
        Spacer(modifier= Modifier.height(16.dp))
        OutlinedTextField(value = password, onValueChange = {password=it },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password",
                    tint = Color(0xFF007BFD)
                )
            },
            colors= OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF007BFD),
                unfocusedBorderColor = Color(0xFF007BFD),
                focusedLabelColor = Color(0xFF007BFD),
                unfocusedLabelColor = Color(0xFF007BFD),
                cursorColor = Color(0xFF007BFD)
            ),
            label= { Text(text = "Password", color = Color(0xFF007BFD), fontWeight = FontWeight.W600) },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(56.dp)
        )
        Spacer(modifier=Modifier.height(50.dp))

        Button(onClick = {
            authViewModel.signUp(email,password)
        },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007BFD),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(17.dp),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(50.dp)
            ) {
            Text(text = "Create Account")
        }
        Spacer(modifier = Modifier.height(16.dp))


        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(vertical = 16.dp)
        ) {
            Divider(
                color = Color.Gray,
                thickness = 2.dp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Or",
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 8.dp),
                fontWeight = FontWeight.SemiBold,
                fontSize = 21.sp
            )
            Divider(
                color = Color.Gray,
                thickness = 2.dp,
                modifier = Modifier.weight(1f)
            )
        }
        TextButton(onClick = {
            navController.navigate("login")
        }) {
            Text("Already have an Account? login",color = Color(0xFF007BFD))
        }
    }
}