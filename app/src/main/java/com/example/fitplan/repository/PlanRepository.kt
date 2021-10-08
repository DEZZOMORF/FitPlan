package com.example.fitplan.repository

import com.example.fitplan.manager.NetworkConnectionManager
import com.example.fitplan.model.Plan
import com.example.fitplan.model.PlanListResponse
import com.example.fitplan.model.PlanResponse
import com.example.fitplan.retrofit.ApiService
import com.example.fitplan.retrofit.PlanNetworkMapper
import com.example.fitplan.room.PlanCacheMapper
import com.example.fitplan.room.PlanDao
import com.example.fitplan.util.DataState
import com.example.fitplan.util.UserException
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlanRepository @Inject constructor(
    private val planDao: PlanDao,
    private val apiService: ApiService,
    private val planNetworkMapper: PlanNetworkMapper,
    private val planCacheMapper: PlanCacheMapper,
    private val networkConnectionManager: NetworkConnectionManager,
) {

    suspend fun getList(): DataState<List<Plan>>? {
        DataState.Loading
        return when (networkConnectionManager.isConnected.value) {
            true -> getListNetwork()
            else -> getListDataBase()
        }
    }

    suspend fun getPlan(id: Int): DataState<Plan>? {
        DataState.Loading
        return when (networkConnectionManager.isConnected.value) {
            true -> getPlanNetwork(id)
            else -> getPlanDataBase(id)
        }
    }

    private suspend fun getListNetwork(): DataState<List<Plan>>? {
        return try {
            val response = apiService.getList()
            if (response.isSuccessful) {
                insertListToDataBase(response)
            } else {
                DataState.UserExceptionState(UserException(response.code()))
            }
            getListDataBase()
        } catch (e: Exception) {
            DataState.Error(e)
        }
    }

    private suspend fun getListDataBase(): DataState<List<Plan>>? {
        return try {
            val cachedPlans = planDao.get()
            DataState.Success(planCacheMapper.mapFromEntityList(cachedPlans))
        } catch (e: Exception) {
            DataState.Error(e)
        }
    }

    private suspend fun insertListToDataBase(response: Response<PlanListResponse>) {
        response.body()?.let {
            val plans = planNetworkMapper.mapFromEntityList(it.result)
            for (plan in plans!!) {
                planDao.insert(planCacheMapper.mapToEntity(plan))
            }
        }
    }

    private suspend fun getPlanNetwork(id: Int): DataState<Plan>? {
        DataState.Loading
        return try {
            val response = apiService.getPlan(id)
            if (response.isSuccessful) {
                insertPlanToDataBase(response)
            } else {
                DataState.UserExceptionState(UserException(response.code()))
            }
            getPlanDataBase(id)
        } catch (e: Exception) {
            DataState.Error(e)
        }
    }

    private suspend fun getPlanDataBase(id: Int): DataState<Plan>? {
        DataState.Loading
        return try {
            val cachedPlans = planDao.getById(id)
            DataState.Success(planCacheMapper.mapFromEntity(cachedPlans))
        } catch (e: Exception) {
            DataState.Error(e)
        }
    }

    private suspend fun insertPlanToDataBase(response: Response<PlanResponse>) {
        response.body()?.let {
            val plan = planNetworkMapper.mapFromEntity(it.result)
            planDao.insert(planCacheMapper.mapToEntity(plan))
        }
    }
}