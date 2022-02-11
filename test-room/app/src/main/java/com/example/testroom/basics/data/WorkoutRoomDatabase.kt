package com.example.testroom.basics.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(WorkoutBasics::class), version = 2, exportSchema = false)
abstract class WorkoutBasicsRoomDatabase : RoomDatabase() {
    abstract fun workoutBasicsDao(): WorkoutBasicsDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: WorkoutBasicsRoomDatabase? = null
        private const val DATABASE_NAME = "workout_basics_database"
        private const val BASIC_DB_TAG = "Basic Db TAG"

        fun getDatabase(context: Context): WorkoutBasicsRoomDatabase {
            // If the INSTANCE is not null, then return it,
            //  if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                Log.i(BASIC_DB_TAG, "Db instance is null.")
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WorkoutBasicsRoomDatabase::class.java,
                    DATABASE_NAME
                // todo: probable don't allow destructive migration in a real app
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}