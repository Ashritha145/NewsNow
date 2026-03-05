package com.example.newsnow.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsnow.data.model.messageModel
import com.example.newsnow.utlis.Constants.Companion.GEMINI_API_KEY
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class ChatBotViewModel(): ViewModel() {

    val messageList by lazy {
        mutableStateListOf<messageModel>()
    }
    val generativeModel: GenerativeModel = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = GEMINI_API_KEY
    )
    fun sendMessage(question:String){
//        Log.i("In chatViewModel",question)
        viewModelScope.launch {
            val chat= generativeModel.startChat(
                history = messageList.map {
                    content(it.role){ text(it.message)}
                }.toList()
            )

            messageList.add(messageModel(question,"user"))
            messageList.add(messageModel("Typing...","model"))

            val response = chat.sendMessage(question)
            messageList.removeAt(messageList.lastIndex)
            messageList.add(messageModel(response.text.toString(),"model"))
        }
    }
}