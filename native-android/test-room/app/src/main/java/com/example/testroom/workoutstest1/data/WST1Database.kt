package com.example.testroom.workoutstest1.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testroom.workoutstest1.data.daos.WST1GroupDao
import com.example.testroom.workoutstest1.data.daos.WST1SetDao
import com.example.testroom.workoutstest1.data.daos.WST1WorkoutDao
import com.example.testroom.workoutstest1.data.entities.WST1Group
import com.example.testroom.workoutstest1.data.entities.WST1Set
import com.example.testroom.workoutstest1.data.entities.WST1Workout

@Database(entities = [WST1Group::class, WST1Workout::class, WST1Set::class],
    version = 7,
    exportSchema = false)
abstract class WST1Database : RoomDatabase() {

    abstract fun groupDao(): WST1GroupDao
    abstract fun workoutDao(): WST1WorkoutDao
    abstract fun setDao(): WST1SetDao

    companion object {
        @Volatile
        private var INSTANCE: WST1Database? = null
        private const val DATABASE_NAME = "wst1_database"

        fun getInstance(context: Context): WST1Database {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WST1Database::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}