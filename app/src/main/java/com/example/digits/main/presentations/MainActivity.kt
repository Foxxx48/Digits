package com.example.digits.main.presentations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.digits.R
import com.example.digits.numbers.presentation.NumbersFragment
import java.security.Key

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, NumbersFragment())
                .commit()
    }

    companion object {
        private const val KEY = "Key"

        fun myLog(any: Any?) {
            Log.d(KEY, "$any")
        }
    }

}