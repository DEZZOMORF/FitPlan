package com.example.fitplan.model

import com.example.fitplan.retrofit.PlanNetworkEntity

data class PlanListResponse(
    val result: List<PlanNetworkEntity>,
    val error: String?
)