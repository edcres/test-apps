package com.example.testroom.onetoone.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(WorkoutOneToOne::class), version = 1, exportSchema = false)
abstract class OneToOneDatabase : RoomDatabase() {

    abstract fun oneToOneDao(): OneToOneDao

    companion object {
        @Volatile
        private var INSTANCE: OneToOneDatabase? = null
        private const val DATABASE_NAME = "workout_one_to_one_database"

        fun getDatabase(context: Context): OneToOneDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OneToOneDatabase::class.java,
                    DATABASE_NAME
                // todo: probable don't allow destructive migration in a real app
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}