package com.example.fitplan.repository

import com.example.fitplan.model.LoginResponse
import com.example.fitplan.retrofit.ApiService
import com.example.fitplan.util.DataState
import com.example.fitplan.util.UserException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val apiService: ApiService,
) {

    suspend fun login(
        username: String,
        password: String,
        clientId: String,
        clientSecret: String,
        grantType: String
    ): DataState<LoginResponse?> {
        DataState.Loading
        return try {
            val response = apiService.login(username, password, clientId, clientSecret, grantType)
            if (response.isSuccessful) {
                DataState.Success(response.body())
            } else {
                DataState.UserExceptionState(UserException(response.code()))
            }
        } catch (e: Exception) {
            DataState.Error(e)
        }
    }
}