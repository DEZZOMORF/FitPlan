package com.example.fitplan.util

sealed class DataState<out R> {

    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val exception: Exception) : DataState<Nothing>()
    data class UserExceptionState(val exception: UserException) : DataState<Nothing>()
    object Loading : DataState<Nothing>()

}