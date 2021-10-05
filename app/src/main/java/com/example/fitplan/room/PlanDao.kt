package com.example.fitplan.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(planEntity: PlanCacheEntity): Long

    @Query("SELECT * FROM plans")
    suspend fun get(): List<PlanCacheEntity>

    @Query("SELECT * FROM plans WHERE id=:id")
    suspend fun getById(id: Int): PlanCacheEntity
}