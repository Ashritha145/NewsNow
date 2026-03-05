package com.example.newsnow.utlis

sealed class NetworkResponse<out T>{

    object Loading: NetworkResponse<Nothing>()

    data class Success<T>(val data: T): NetworkResponse<T>()

    data class Error<T>(val message:String?): NetworkResponse<T>()
}
