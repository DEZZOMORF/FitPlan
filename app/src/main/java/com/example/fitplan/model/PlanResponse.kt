package com.example.fitplan.model

import com.example.fitplan.retrofit.PlanNetworkEntity

data class PlanResponse (
    val result: PlanNetworkEntity,
    val error: String?
)