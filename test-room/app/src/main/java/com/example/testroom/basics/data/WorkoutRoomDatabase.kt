package com.example.testroom.basics.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(WorkoutBasics::class), version = 1, exportSchema = false)
abstract class WorkoutBasicsRoomDatabase : RoomDatabase() {

    abstract fun workoutBasicsDao(): WorkoutBasicsDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: WorkoutBasicsRoomDatabase? = null
        const val DATABASE_NAME = "workout_basics_database"

        fun getDatabase(context: Context): WorkoutBasicsRoomDatabase {
            // If the INSTANCE is not null, then return it,
            //  if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WorkoutBasicsRoomDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}