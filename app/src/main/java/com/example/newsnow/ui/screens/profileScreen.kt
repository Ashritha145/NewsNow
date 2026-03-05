package com.example.newsnow.ui.screens


import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.newsnow.R
import com.example.newsnow.ui.viewmodel.AuthState
import com.example.newsnow.ui.viewmodel.AuthViewModel
import com.example.newsnow.ui.viewmodel.NewsViewModel
import com.example.newsnow.utlis.PreferenceManager
import kotlin.let


@Composable
fun ProfileScreen(viewModel: AuthViewModel, navController: NavController, newsViewModel: NewsViewModel){

    val email=viewModel.getUserEmail()
    var showEditDialog by remember { mutableStateOf(false) }
    val authState=viewModel.authState.observeAsState()
    val context=LocalContext.current
    val userPrefs = remember { PreferenceManager(context, viewModel) }
    LaunchedEffect(
        authState.value
    ) {
        when(authState.value){
            is AuthState.Unauthenticated-> navController.navigate("login")
            else-> Unit
        }
    }
    Column(modifier= Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "profile",
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(color = Color.White)
                .padding(8.dp),
            tint = Color(0xFF007BFD)
        )
        Text(text = "Profile", fontWeight = FontWeight.Bold, fontSize = 32.sp)
        Text(text = "Welcome, ${userPrefs.username}", fontWeight = FontWeight.SemiBold, color = Color.Gray)
        Spacer(modifier=Modifier.height(15.dp))

        ProfileContainer(icon = IconSource.Vector(Icons.Default.Person), title = "Username", subtitle = userPrefs.username)
        ProfileContainer(icon = IconSource.Vector(Icons.Default.Email), title = "Email", subtitle = email)
        ProfileContainer(icon = IconSource.Vector(Icons.Default.Edit), title = "Edit Profile"){
            showEditDialog=true
        }
        ProfileContainer(icon = IconSource.Drawable(R.drawable.stash_save_ribbon), title = "Saved Articles"){
            navController.navigate("Saved")
        }
        Spacer(modifier = Modifier.height(15.dp))
        Button(onClick = {
            viewModel.signOut()
        }, modifier = Modifier
            .fillMaxWidth(0.7f)
            ,
            colors= ButtonDefaults.buttonColors(containerColor = Color.Red,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(17.dp)

        ){
            Text(text = "Log Out")
        }
       if (showEditDialog){
           EditUserNameDialog(
               currentUserName = userPrefs.username, onDismiss = {showEditDialog=false},
               onSave = { newName->
                   userPrefs.saveUserName(newName)
                   showEditDialog=false
               }
               )
       }
    }
}
@Composable
fun ProfileContainer(
    icon: IconSource,
    title:String,
    subtitle:String?=null,
    onClick: (()-> Unit)? = null

){
    Card(modifier= Modifier.fillMaxWidth()
        .padding(20.dp)
        .clickable{
            onClick?.invoke()
        }
        ,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Surface{
            Row(modifier=Modifier.fillMaxWidth()
                .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    when(icon){
                        is IconSource.Vector->
                            Icon(imageVector = icon.imageVector, contentDescription = title,
                                tint = Color.Unspecified
                            )
                        is IconSource.Drawable->
                            Icon(painter = painterResource(id = icon.resId), contentDescription = null)
                    }

                    Spacer(modifier=Modifier.width(20.dp))
                    Column {
                        Text(text = title, fontWeight = FontWeight.SemiBold)
                        subtitle?.let {
                            Text(text = it, fontWeight = FontWeight.W600,color=Color.Gray)
                        }

                    }
                }
            }
        }

    }
}
sealed class IconSource{
    data class Vector(val imageVector: ImageVector): IconSource()
    data class Drawable(@DrawableRes val resId:Int): IconSource()
}
@Composable
fun EditUserNameDialog(
    currentUserName: String?,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
){
    var username by remember { mutableStateOf(currentUserName) }
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Edit Username") },
        text = {
            username?.let { it1 ->
                OutlinedTextField(
                    value = it1,
                    onValueChange = {username=it},
                    label = {Text(text = "Your Username")},
                    placeholder = {Text(text = "Enter your New Username")},
                    singleLine = true,
                    modifier=Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                username?.let { onSave(it) }
                Toast.makeText(context,"Changes Saved Successfully",Toast.LENGTH_SHORT).show()
                onDismiss()
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE53935),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(6.dp)
                ) {
                Text(text = "Save")
            }
        },
        dismissButton = {
            TextButton(onClick = {onDismiss()}) {
                Text(text = "Cancel", color = Color.Black)
            }
        }

    )
}