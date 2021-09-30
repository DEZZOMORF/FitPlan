package com.example.fitplan.repository

import com.example.fitplan.model.LoginResponse
import com.example.fitplan.model.Plan
import com.example.fitplan.retrofit.PlanNetworkMapper
import com.example.fitplan.retrofit.RetrofitService
import com.example.fitplan.room.PlanCacheMapper
import com.example.fitplan.room.PlanDao
import com.example.test.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FitPlanRepository @Inject constructor(
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
    ): Flow<DataState<LoginResponse>> = flow {
        emit(DataState.Loading)
        try {
            val response = retrofitService.login(username, password, clientId, clientSecret, grantType)
            emit(DataState.Success(response))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getList(): Flow<DataState<List<Plan>>> = flow {
        emit(DataState.Loading)
        try {
            val networkPlans = retrofitService.getList()
            val plans = planNetworkMapper.mapFromEntityList(networkPlans.result)
            for (plan in plans) {
                planDao.insert(planCacheMapper.mapToEntity(plan))
            }
            val cachedPlans = planDao.get()
            emit(DataState.Success(planCacheMapper.mapFromEntityList(cachedPlans)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getPlan(id: Int): Flow<DataState<Plan>> = flow {
        emit(DataState.Loading)
        try {
            val networkPlan = retrofitService.getPlan(id)
            val plan = planNetworkMapper.mapFromEntity(networkPlan.result)
            planDao.insert(planCacheMapper.mapToEntity(plan))
            val cachedPlans = planDao.getById(id)
            emit(DataState.Success(planCacheMapper.mapFromEntity(cachedPlans)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}