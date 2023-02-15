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

    private lateinit var database: NumbersDatabase
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
        assertEquals(null, dao.number("4"))

        dao.insert(number)
        val actualList = dao.allNumbers()
        assertEquals(number, actualList[0])

        val actual = dao.number("4")
        assertEquals(number, actual)
    }

    @Test
    fun test_add_2_times() {
        val number = NumbersCache("4", "4", 10)
        dao.insert(number)
        var actualList = dao.allNumbers()
        assertEquals(number, actualList[0])

        val new = NumbersCache("4", "4", 15)
        dao.insert(new)
        actualList = dao.allNumbers()
        assertEquals(1, actualList.size)
        assertEquals(new, actualList[0])
    }

}