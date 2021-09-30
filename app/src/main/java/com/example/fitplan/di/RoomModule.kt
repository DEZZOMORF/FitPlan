package com.example.fitplan.di

import android.content.Context
import androidx.room.Room
import com.example.fitplan.room.PlanDao
import com.example.fitplan.room.FitPlanDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideFitPlanDb(@ApplicationContext context: Context): FitPlanDatabase {
        return Room.databaseBuilder(
            context,
            FitPlanDatabase::class.java,
            FitPlanDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providePlanDAO(fitPlanDatabase: FitPlanDatabase): PlanDao {
        return fitPlanDatabase.planDao()
    }

}