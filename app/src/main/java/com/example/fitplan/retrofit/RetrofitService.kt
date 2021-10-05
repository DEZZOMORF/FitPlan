package com.example.fitplan.retrofit

import com.example.fitplan.model.LoginResponse
import com.example.fitplan.model.PlanListResponse
import com.example.fitplan.model.PlanResponse
import com.example.fitplan.util.NetworkUrls
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {

    @FormUrlEncoded
    @POST(NetworkUrls.LOGIN)
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String,
    ): Response<LoginResponse>

    @GET(NetworkUrls.PLANS_LIST)
    suspend fun getList(): Response<PlanListResponse>

    @GET(NetworkUrls.PLAN_DETAILS)
    suspend fun getPlan(@Query("planId") id: Int): Response<PlanResponse>

}