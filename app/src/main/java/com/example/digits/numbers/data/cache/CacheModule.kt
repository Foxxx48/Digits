package com.example.digits.numbers.data.cache

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

interface CacheModule {
    fun provideDatabase(): NumbersDatabase

    class Base(private val context: Context) : CacheModule {

        private val database by lazy {
            return@lazy Room.databaseBuilder(
                context,
                NumbersDatabase::class.java,
                "numbers_database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }

        override fun provideDatabase(): NumbersDatabase {
            return database
        }
    }
}