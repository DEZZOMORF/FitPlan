package com.example.fitplan.model

data class Plan(

    val id: Int,
    val name: String?,
    val imageUrl: String?,
    val imageSmallUrl: String?,
    val athleteId: Int?,
    val athleteFirstName: String?,
    val athleteLastName: String?,
    val athleteSlug: String?,
    val slug: String?,
    val singleLength: Int?,
    val daysCount: Int?,
    val sex: String?,
    val daysPerWeek: Int?,
    val location: Int?,
    val type: Int?,
    val presentationType: Int?,
    val metadata: String?,
    val displayPriority: Int?,
    val free: Boolean?,
    val modifiedTimestamp: Long?,
    val available: Boolean?,
    val allowFreeAccess: Boolean?,
    val imageTvUrl: String?,
    val description: String?

)