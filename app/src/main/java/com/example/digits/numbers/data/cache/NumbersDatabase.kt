package com.example.digits.numbers.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NumbersCache::class], version = 1)
abstract class NumbersDatabase : RoomDatabase() {
    abstract fun numbersDao() : NumbersDao
}