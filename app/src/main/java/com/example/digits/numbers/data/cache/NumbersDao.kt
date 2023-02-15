package com.example.digits.numbers.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NumbersDao {

    @Query(value = "SELECT * FROM numbers_table ORDER BY date ASC")
    fun allNumbers(): List<NumbersCache>

    @Query(value = "SELECT * FROM numbers_table WHERE number = :number LIMIT 1")
    fun number(number: String): NumbersCache?

    @Insert(entity = NumbersCache::class, onConflict = OnConflictStrategy.REPLACE)
    fun insert(number: NumbersCache)

//    @Query(value = "SELECT * FROM numbers_table WHERE number = :number")
//    fun contains(number: String) : Boolean


}