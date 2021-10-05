package com.example.fitplan.repository

import com.example.fitplan.model.Plan
import com.example.fitplan.retrofit.PlanNetworkMapper
import com.example.fitplan.retrofit.ApiService
import com.example.fitplan.room.PlanCacheMapper
import com.example.fitplan.room.PlanDao
import com.example.fitplan.util.DataState
import com.example.fitplan.util.UserException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlanRepository @Inject constructor(
    private val planDao: PlanDao,
    private val apiService: ApiService,
    private val planNetworkMapper: PlanNetworkMapper,
    private val planCacheMapper: PlanCacheMapper
) {

    suspend fun getList(): Flow<DataState<List<Plan>>> = flow {
        emit(DataState.Loading)
        try {
            val response = apiService.getList()
            if (response.isSuccessful) {
                response.body()?.let {
                    val plans = planNetworkMapper.mapFromEntityList(response.body()?.result)
                    for (plan in plans!!) {
                        planDao.insert(planCacheMapper.mapToEntity(plan))
                    }
                    val cachedPlans = planDao.get()
                    emit(DataState.Success(planCacheMapper.mapFromEntityList(cachedPlans)))
                }
            } else {
                emit(DataState.UserExceptionState(UserException(response.code())))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getPlan(id: Int): Flow<DataState<Plan>> = flow {
        emit(DataState.Loading)
        try {
            val response = apiService.getPlan(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    val plan = planNetworkMapper.mapFromEntity(it.result)
                    planDao.insert(planCacheMapper.mapToEntity(plan))
                    val cachedPlans = planDao.getById(id)
                    emit(DataState.Success(planCacheMapper.mapFromEntity(cachedPlans)))
                }
            } else {
                emit(DataState.UserExceptionState(UserException(response.code())))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}