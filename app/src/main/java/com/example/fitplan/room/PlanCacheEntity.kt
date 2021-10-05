package com.example.fitplan.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plans")
data class PlanCacheEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "imageUrl")
    val imageUrl: String?,

    @ColumnInfo(name = "imageSmallUrl")
    val imageSmallUrl: String?,

    @ColumnInfo(name = "athleteId")
    val athleteId: Int?,

    @ColumnInfo(name = "athleteFirstName")
    val athleteFirstName: String?,

    @ColumnInfo(name = "athleteLastName")
    val athleteLastName: String?,

    @ColumnInfo(name = "athleteSlug")
    val athleteSlug: String?,

    @ColumnInfo(name = "slug")
    val slug: String?,

    @ColumnInfo(name = "singleLength")
    val singleLength: Int?,

    @ColumnInfo(name = "daysCount")
    val daysCount: Int?,

    @ColumnInfo(name = "sex")
    val sex: String?,

    @ColumnInfo(name = "daysPerWeek")
    val daysPerWeek: Int?,

    @ColumnInfo(name = "location")
    val location: Int?,

    @ColumnInfo(name = "type")
    val type: Int?,

    @ColumnInfo(name = "presentationType")
    val presentationType: Int?,

    @ColumnInfo(name = "metadata")
    val metadata: String?,

    @ColumnInfo(name = "displayPriority")
    val displayPriority: Int?,

    @ColumnInfo(name = "free")
    val free: Boolean?,

    @ColumnInfo(name = "modifiedTimestamp")
    val modifiedTimestamp: Long?,

    @ColumnInfo(name = "available")
    val available: Boolean?,

    @ColumnInfo(name = "allowFreeAccess")
    val allowFreeAccess: Boolean?,

    @ColumnInfo(name = "imageTvUrl")
    val imageTvUrl: String?,

    @ColumnInfo(name = "description")
    val description: String?
)