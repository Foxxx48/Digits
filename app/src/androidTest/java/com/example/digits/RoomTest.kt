package com.example.digits

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.digits.numbers.data.cache.NumbersCache
import com.example.digits.numbers.data.cache.NumbersDao
import com.example.digits.numbers.data.cache.NumbersDatabase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var database : NumbersDatabase
    private lateinit var dao: NumbersDao

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        database = Room.inMemoryDatabaseBuilder(context, NumbersDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.numbersDao()
    }

    @After
    @Throws(IOException::class)
    fun clear() {
        database.close()
    }

    @Test
    fun test_add_and_check() {
        val number = NumbersCache("4", "4", 10)

        dao.saveNumber(number)

        val actual = dao.allNumbers()

        assertEquals(number, actual[0])
    }

}