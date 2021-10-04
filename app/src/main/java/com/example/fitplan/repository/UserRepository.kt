package com.example.fitplan.repository

import com.example.fitplan.model.LoginResponse
import com.example.fitplan.retrofit.PlanNetworkMapper
import com.example.fitplan.retrofit.RetrofitService
import com.example.fitplan.room.PlanCacheMapper
import com.example.fitplan.room.PlanDao
import com.example.fitplan.util.DataState
import com.example.fitplan.util.UserException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val planDao: PlanDao,
    private val retrofitService: RetrofitService,
    private val planNetworkMapper: PlanNetworkMapper,
    private val planCacheMapper: PlanCacheMapper
) {

    suspend fun login(
        username: String,
        password: String,
        clientId: String,
        clientSecret: String,
        grantType: String
    ): Flow<DataState<LoginResponse?>> = flow {
        emit(DataState.Loading)
        try {
            val response = retrofitService.login(username, password, clientId, clientSecret, grantType)
            if (response.isSuccessful) {
                emit(DataState.Success(response.body()))
            } else {
                emit(DataState.UserExceptionState(UserException(response.code())))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}