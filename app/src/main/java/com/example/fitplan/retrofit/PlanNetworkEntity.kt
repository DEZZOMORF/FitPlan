package com.example.fitplan.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PlanNetworkEntity(

    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("name")
    @Expose
    val name: String?,

    @SerializedName("imageUrl")
    @Expose
    val imageUrl: String?,

    @SerializedName("imageSmallUrl")
    @Expose
    val imageSmallUrl: String?,

    @SerializedName("athleteId")
    @Expose
    val athleteId: Int?,

    @SerializedName("athleteFirstName")
    @Expose
    val athleteFirstName: String?,

    @SerializedName("athleteLastName")
    @Expose
    val athleteLastName: String?,

    @SerializedName("athleteSlug")
    @Expose
    val athleteSlug: String?,

    @SerializedName("slug")
    @Expose
    val slug: String?,

    @SerializedName("singleLength")
    @Expose
    val singleLength: Int?,

    @SerializedName("daysCount")
    @Expose
    val daysCount: Int?,

    @SerializedName("sex")
    @Expose
    val sex: String?,

    @SerializedName("daysPerWeek")
    @Expose
    val daysPerWeek: Int?,

    @SerializedName("location")
    @Expose
    val location: Int?,

    @SerializedName("type")
    @Expose
    val type: Int?,

    @SerializedName("presentationType")
    @Expose
    val presentationType: Int?,

    @SerializedName("metadata")
    @Expose
    val metadata: String?,

    @SerializedName("displayPriority")
    @Expose
    val displayPriority: Int?,

    @SerializedName("free")
    @Expose
    val free: Boolean?,

    @SerializedName("modifiedTimestamp")
    @Expose
    val modifiedTimestamp: Long?,

    @SerializedName("available")
    @Expose
    val available: Boolean?,

    @SerializedName("allowFreeAccess")
    @Expose
    val allowFreeAccess: Boolean?,

    @SerializedName("imageTvUrl")
    @Expose
    val imageTvUrl: String?,

    @SerializedName("description")
    @Expose
    val description: String?,

    )