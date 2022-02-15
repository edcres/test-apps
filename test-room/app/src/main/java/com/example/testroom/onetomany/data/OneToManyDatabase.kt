package com.example.testroom.onetomany.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testroom.onetomany.data.entities.Director
import com.example.testroom.onetomany.data.entities.School

@Database(
    entities = [
        School::class,
        Director::class
    ],
    version = 1,
    exportSchema = false
)
abstract class OneToManyDatabase : RoomDatabase() {

    abstract val oneToManyDao: OneToManyDao

    companion object {
        @Volatile
        private var INSTANCE: OneToManyDatabase? = null
        private const val DATABASE_NAME = "one_to_many_database"

        fun getDatabase(context: Context): OneToManyDatabase {
            return OneToManyDatabase.INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OneToManyDatabase::class.java,
                    DATABASE_NAME
                    // todo: probably don't allow destructive migration in a real app
                ).fallbackToDestructiveMigration().build()
                OneToManyDatabase.INSTANCE = instance
                instance
            }
        }
    }
}