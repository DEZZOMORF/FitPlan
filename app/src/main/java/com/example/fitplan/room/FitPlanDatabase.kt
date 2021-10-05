package com.example.fitplan.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PlanCacheEntity::class], version = 1)
abstract class FitPlanDatabase : RoomDatabase() {

    abstract fun planDao(): PlanDao

    companion object {
        const val DATABASE_NAME: String = "fit_db"
    }
}